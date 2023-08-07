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
        channel.pipeline().addLast(new IdleStateHandler(0, 0, 30, TimeUnit.SECONDS));

        controller.sendPacketUnencrypted(PacketId.InitializeCrypto, controller.getEncryption().encryptionPacket());
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) {
        if(evt instanceof IdleStateEvent event) if(event.state() == IdleState.ALL_IDLE) ctx.close();
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) {
        logger.info("Channel is inactive!");
        controller.Dispose();
    }
}
