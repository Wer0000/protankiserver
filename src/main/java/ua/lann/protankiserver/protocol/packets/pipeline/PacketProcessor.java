package ua.lann.protankiserver.protocol.packets.pipeline;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.lann.protankiserver.protocol.Encryption;
import ua.lann.protankiserver.protocol.packets.PacketProcessorState;

import java.util.List;

public class PacketProcessor extends ByteToMessageDecoder {
    private final Logger logger = LoggerFactory.getLogger(PacketProcessor.class);

    private final Encryption encryption;
    public PacketProcessor(Encryption encryption) { this.encryption = encryption; };

    private PacketProcessorState state = PacketProcessorState.Waiting;
    private final ByteBuf buffer = Unpooled.buffer();
    private int packetLength;

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf buf, List<Object> out) throws Exception {
        buffer.writeBytes(buf);

        if(state == PacketProcessorState.Waiting) {
            if(buffer.readableBytes() < 4) return;

            packetLength = buffer.readInt() - 4; // Lann: Dev is idiot
            buffer.discardReadBytes();

            state = PacketProcessorState.Processing;
        }

        while(state == PacketProcessorState.Processing) {
            if(buffer.readableBytes() < packetLength) return;

            ByteBuf read = buffer.readBytes(packetLength);
            if(buffer.readableBytes() >= 4) packetLength = buffer.readInt() - 4;
            else state = PacketProcessorState.Waiting;

            buffer.discardReadBytes();

            read.retain();
            out.add(read);
        }
    }
}
