package ua.lann.protankiserver;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import lombok.Getter;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.lann.protankiserver.game.battles.BattleBase;
import ua.lann.protankiserver.game.battles.BattlesManager;
import ua.lann.protankiserver.game.battles.impl.DeathMatchBattle;
import ua.lann.protankiserver.enums.BattleMode;
import ua.lann.protankiserver.enums.BattleSuspictionLevel;
import ua.lann.protankiserver.enums.Rank;
import ua.lann.protankiserver.game.lobbychat.LobbyChat;
import ua.lann.protankiserver.game.battles.MapManager;
import ua.lann.protankiserver.models.battle.BattleSettings;
import ua.lann.protankiserver.models.battle.ProBattleSettings;
import ua.lann.protankiserver.orm.HibernateUtils;
import ua.lann.protankiserver.orm.entities.Player;
import ua.lann.protankiserver.game.protocol.packets.CodecRegistry;
import ua.lann.protankiserver.game.protocol.packets.PacketId;

import java.net.InetSocketAddress;
import java.util.HashMap;

public class Server {
    private static final Logger logger = LoggerFactory.getLogger(Server.class);

    @Getter private static Server instance;
    @Getter private LobbyChat lobbyChat;

    private HashMap<String, ClientController> controllers;

    public static final int PORT = 1338;

    public void run() {
        instance = this;
        controllers = new HashMap<>();

        BattleBase battle = new DeathMatchBattle(
                "ffffffff",
                "Песочница DM",
                MapManager.getMap("map_sandbox"),
                new BattleSettings(
                        BattleMode.DM,
                        16,
                        Rank.Generalissimo,
                        Rank.Recruit,
                        100,
                        60 * 15
                ),
                BattleSuspictionLevel.None,
                false,
                new ProBattleSettings()
        );

        BattlesManager.addBattle(battle);

        logger.info("Starting lobby chat...");
        lobbyChat = new LobbyChat(this);

        logger.info("Launching protanki server...");

        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workerGroup)
                .channel(NioServerSocketChannel.class)
                .localAddress(new InetSocketAddress("0.0.0.0", PORT))
//                .childOption(ChannelOption.SO_KEEPALIVE, true)
                .childHandler(new ClientChannelInitializer());

            ChannelFuture f = b.bind().sync();
            logger.info("TCP server started on port {}", PORT);

            f.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            logger.error("Error occurred while starting server:", e);
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }

    public void broadcast(PacketId packetId, ByteBuf buffer) {
        controllers.values().forEach(x -> x.sendPacket(packetId, buffer));
    }

    public void removeActiveController(ClientController controller) {
        this.controllers.remove(controller.getPlayer().getNickname());
        this.controllers.values().forEach(x -> {
            ByteBuf buf = Unpooled.buffer();

            CodecRegistry.getCodec(Boolean.class).encode(buf, false);
            CodecRegistry.getCodec(String.class).encode(buf, controller.getPlayer().getNickname());

            x.sendPacket(PacketId.SetUserOnlineInfo, buf);
            buf.release();
        });
    }

    public void addActiveController(ClientController controller) {
        this.controllers.put(controller.getPlayer().getNickname(), controller);
    }

    public Player getPlayer(String nickname) {
        ClientController ctl = tryGetOnlinePlayerController(nickname);
        if(ctl != null) return ctl.getPlayer();

        try(Session session = HibernateUtils.session()) {
            return session.get(Player.class, nickname);
        }
    }

    public ClientController tryGetOnlinePlayerController(String nickname) {
        return this.controllers.get(nickname);
    }
}
