package ua.lann.protankiserver;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;

public class Server {
    public static final int PORT = 1338;
    private static final Logger logger = LoggerFactory.getLogger(Server.class);

    public void run() {
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
}
