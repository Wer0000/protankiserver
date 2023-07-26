package ua.lann.protankiserver.protocol.packets.codec;

import io.netty.buffer.ByteBuf;
import ua.lann.protankiserver.protocol.packets.CodecRegistry;

import java.nio.charset.StandardCharsets;

public class UTFStringCodec implements ICodec<String> {
    @Override
    public void encode(ByteBuf buf, String data) {
        ICodec<Boolean> codec = CodecRegistry.getCodec(Boolean.class);

        if(data == null) codec.encode(buf, true);
        else {
            codec.encode(buf, false);

            byte[] bytes = data.getBytes(StandardCharsets.UTF_8);
            buf.writeInt(bytes.length);
            buf.writeBytes(bytes);
        }
    }

    @Override
    public String decode(ByteBuf data) {
        ICodec<Boolean> codec = CodecRegistry.getCodec(Boolean.class);
        boolean isEmpty = codec.decode(data);
        if(!isEmpty) {
            int length = data.readInt();
            return data.readBytes(length).toString(StandardCharsets.UTF_8);
        }

        return null;
    }
}
