package ua.lann.protankiserver.protocol.packets.pipeline;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.lann.protankiserver.ClientController;
import ua.lann.protankiserver.protocol.Encryption;
import ua.lann.protankiserver.protocol.packets.PacketId;
import ua.lann.protankiserver.protocol.packets.handlers.IHandler;
import ua.lann.protankiserver.protocol.packets.PacketHandlersRegistry;

public class PacketHandler extends ChannelInboundHandlerAdapter {
    private final Logger logger = LoggerFactory.getLogger(PacketHandler.class);

    private final Encryption encryption;
    private final ClientController channel;

    public PacketHandler(ClientController channel, Encryption encryption) {
        this.channel = channel;
        this.encryption = encryption;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf buffer = (ByteBuf) msg;
        buffer.retain();

        int rawPacketId = buffer.readInt();
        PacketId packetId = PacketId.getByPacketId(rawPacketId);

        buffer = encryption.decrypt(buffer);

        if(packetId == null) {
            logger.warn("Unknown incoming packet id: {}", rawPacketId);
            return;
        }

        logger.info("Incoming packet [{}]: {}", packetId, ClientController.toHexString(buffer));

        IHandler handler = PacketHandlersRegistry.getHandler(packetId);
        if(handler == null) logger.error("Missing handler for packet id {}", packetId);
        else handler.handle(channel, buffer);
    }
}
