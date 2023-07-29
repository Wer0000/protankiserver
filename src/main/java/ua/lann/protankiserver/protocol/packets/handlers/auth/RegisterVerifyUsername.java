package ua.lann.protankiserver.protocol.packets.handlers.auth;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import ua.lann.protankiserver.ClientController;
import ua.lann.protankiserver.protocol.packets.CodecRegistry;
import ua.lann.protankiserver.protocol.packets.PacketId;
import ua.lann.protankiserver.protocol.packets.codec.ICodec;
import ua.lann.protankiserver.protocol.packets.handlers.IHandler;

public class RegisterVerifyUsername implements IHandler {
    @Override
    public void handle(ClientController channel, ByteBuf buf) {
        ICodec<String> stringICodec = CodecRegistry.getCodec(String.class);

        String username = stringICodec.decode(buf);
        if(username.equals("Lann")) {
            ByteBuf buffer = Unpooled.buffer();
            buffer.writeByte(0);
            buffer.writeInt(5);
            stringICodec.encode(buffer, "tieblan");
            stringICodec.encode(buffer, "tidolbaeb");
            stringICodec.encode(buffer, "tisuka");
            stringICodec.encode(buffer, "tidebil");
            stringICodec.encode(buffer, "koncheny");

            channel.sendPacket(PacketId.RecommendedNames, buffer);
        } else channel.sendPacket(PacketId.UsernameAvailable, Unpooled.buffer());

    }
}
