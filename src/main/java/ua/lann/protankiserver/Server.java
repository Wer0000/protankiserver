package ua.lann.protankiserver;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.lann.protankiserver.battles.BattleBase;
import ua.lann.protankiserver.battles.BattlesManager;
import ua.lann.protankiserver.battles.impl.DeathMatchBattle;
import ua.lann.protankiserver.enums.BattleMode;
import ua.lann.protankiserver.enums.BattleSuspictionLevel;
import ua.lann.protankiserver.enums.Rank;
import ua.lann.protankiserver.lobbychat.LobbyChat;
import ua.lann.protankiserver.battles.map.MapManager;
import ua.lann.protankiserver.models.battle.BattleSettings;
import ua.lann.protankiserver.models.profile.PlayerProfile;
import ua.lann.protankiserver.models.battle.ProBattleSettings;

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

        logger.info("Loading maps...");
        MapManager.loadMaps();

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
                .childOption(ChannelOption.SO_KEEPALIVE, true)
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

    public void addActiveController(ClientController controller) {
        this.controllers.put(controller.getProfile().getNickname(), controller);
    }

    public ClientController tryGetOnlinePlayerController(String nickname) {
        return this.controllers.get(nickname);
    }

    public PlayerProfile tryGetOnlinePlayerProfile(String nickname) {
        // TODO: Retrieve player profile or null if player is offline
        return null;
    }
}
