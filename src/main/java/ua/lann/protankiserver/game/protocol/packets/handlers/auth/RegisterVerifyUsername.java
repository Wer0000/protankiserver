package ua.lann.protankiserver.game.protocol.packets.handlers.auth;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import ua.lann.protankiserver.ClientController;
import ua.lann.protankiserver.reflection.annotations.PacketHandler;
import ua.lann.protankiserver.orm.entities.Player;
import ua.lann.protankiserver.game.protocol.packets.CodecRegistry;
import ua.lann.protankiserver.game.protocol.packets.PacketId;
import ua.lann.protankiserver.game.protocol.packets.codec.ICodec;
import ua.lann.protankiserver.game.protocol.packets.handlers.IHandler;

@PacketHandler(packetId = PacketId.RegisterVerifyUsername)
public class RegisterVerifyUsername implements IHandler {
    @Override
    public void handle(ClientController channel, ByteBuf buf) {
        ICodec<String> stringICodec = CodecRegistry.getCodec(String.class);

        String nickname = stringICodec.decode(buf);
        if(Player.exists(nickname)) {
            ByteBuf buffer = Unpooled.buffer();

            buffer.writeByte(0);
            buffer.writeInt(5);
            stringICodec.encode(buffer, nickname + "_01");
            stringICodec.encode(buffer, nickname + "_02");
            stringICodec.encode(buffer, nickname + "_03");
            stringICodec.encode(buffer, nickname + "_04");
            stringICodec.encode(buffer, nickname + "_05");

            channel.sendPacket(PacketId.RecommendedNames, buffer);
            buffer.release();
        } else channel.sendPacket(PacketId.UsernameAvailable, Unpooled.buffer());
    }
}
