package ua.lann.protankiserver;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.handler.timeout.IdleStateHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.lann.protankiserver.game.protocol.packets.PacketId;
import ua.lann.protankiserver.game.protocol.packets.pipeline.PacketHandlerAdapter;
import ua.lann.protankiserver.game.protocol.packets.pipeline.PacketProcessor;

import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ClientChannelInitializer extends ChannelInitializer<SocketChannel> {
    private final Logger logger = LoggerFactory.getLogger(ChannelInitializer.class);
    private ClientController controller;

    @Override
    protected void initChannel(SocketChannel channel) {
        controller = new ClientController(channel);
        logger.info("Connection from {}", channel.remoteAddress().getAddress().getHostAddress());

        channel.pipeline().addLast(new PacketProcessor());
        channel.pipeline().addLast(new PacketHandlerAdapter(controller, controller.getEncryption()));

        controller.sendPacketUnencrypted(PacketId.InitializeCrypto, controller.getEncryption().encryptionPacket());
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) {
        // TODO: This is not being called even though channel.isOpen and channel.isActive both false
        // if you know what causes this issue tell me in discord @luminate_d or open an issue

        logger.info("Channel is inactive!");
        controller.Dispose();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        // TODO: This is not being called even though channel.isOpen and channel.isActive both false
        // if you know what causes this issue tell me in discord @luminate_d or open an issue
        logger.error("Exception caught in pipeline:", cause);
    }
}
