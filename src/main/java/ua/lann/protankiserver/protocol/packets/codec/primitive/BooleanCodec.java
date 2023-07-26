package ua.lann.protankiserver.protocol.packets.codec.primitive;

import io.netty.buffer.ByteBuf;
import ua.lann.protankiserver.protocol.packets.codec.ICodec;

public class BooleanCodec implements ICodec<Boolean> {
    @Override
    public void encode(ByteBuf buf, Boolean data) {
        buf.writeByte(data ? 1 : 0);
    }

    @Override
    public Boolean decode(ByteBuf data) {
        return data.readByte() == 1;
    }
}
