package ua.lann.protankiserver.game.protocol.packets.pipeline;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.lann.protankiserver.ClientController;
import ua.lann.protankiserver.game.protocol.Encryption;
import ua.lann.protankiserver.game.protocol.packets.PacketId;
import ua.lann.protankiserver.game.protocol.packets.handlers.IHandler;
import ua.lann.protankiserver.game.protocol.packets.PacketHandlersRegistry;

public class PacketHandlerAdapter extends ChannelInboundHandlerAdapter {
    private final Logger logger = LoggerFactory.getLogger(PacketHandlerAdapter.class);

    private final Encryption encryption;
    private final ClientController channel;

    public PacketHandlerAdapter(ClientController channel, Encryption encryption) {
        this.channel = channel;
        this.encryption = encryption;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
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
        if(handler == null) logger.warn("Missing handler for packet id {}", packetId);
        else handler.handle(channel, buffer);
    }
}
