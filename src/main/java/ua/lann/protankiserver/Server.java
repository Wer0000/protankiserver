package ua.lann.protankiserver;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.lann.protankiserver.lobbychat.LobbyChat;

import java.net.InetSocketAddress;

public class Server {
    private static Server instance;

    @Getter private LobbyChat lobbyChat;

    public static final int PORT = 1338;
    private static final Logger logger = LoggerFactory.getLogger(Server.class);

    public void run() {
        instance = this;

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

    public PlayerProfile tryGetOnlinePlayerProfile(String nickname) {
        // TODO: Retrieve player profile or null if player is offline
        return null;
    }

    public static Server getInstance() {
        return instance;
    }
}
