package ua.lann.protankiserver;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.lann.protankiserver.protocol.packets.PacketId;
import ua.lann.protankiserver.protocol.packets.pipeline.PacketHandler;
import ua.lann.protankiserver.protocol.packets.pipeline.PacketProcessor;

public class ClientChannelInitializer extends ChannelInitializer<SocketChannel> {
    private final Logger logger = LoggerFactory.getLogger(ChannelInitializer.class);

    private ClientController controller;

    @Override
    protected void initChannel(SocketChannel channel) throws Exception {
        controller = new ClientController(channel);
        logger.info("Connection from {}", channel.remoteAddress().getAddress().getHostAddress());

        channel.pipeline().addLast(new PacketProcessor(controller.encryption));
        channel.pipeline().addLast(new PacketHandler(controller, controller.encryption));

        controller.sendPacketUnencrypted(PacketId.InitializeCrypto, controller.encryption.encryptionPacket());
    }
}
