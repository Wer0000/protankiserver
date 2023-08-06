package ua.lann.protankiserver.protocol.packets.handlers.lobby.friends;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import ua.lann.protankiserver.ClientController;
import ua.lann.protankiserver.Server;
import ua.lann.protankiserver.reflection.annotations.PacketHandler;
import ua.lann.protankiserver.orm.entities.Player;
import ua.lann.protankiserver.protocol.packets.CodecRegistry;
import ua.lann.protankiserver.protocol.packets.PacketId;
import ua.lann.protankiserver.protocol.packets.codec.ICodec;
import ua.lann.protankiserver.protocol.packets.handlers.IHandler;

@PacketHandler(packetId = PacketId.VerifyRequestedNicknameExists)
public class VerifyRequestedNicknameExists implements IHandler {
    @Override
    public void handle(ClientController channel, ByteBuf buf) {
        ICodec<String> stringICodec = CodecRegistry.getCodec(String.class);
        String nickname = stringICodec.decode(buf);

        if(nickname.equals(channel.getPlayer().getNickname())) {
            channel.sendPacket(PacketId.RequestedNicknameNotExists, Unpooled.buffer());
            return;
        }

        Player player = Server.getInstance().getPlayer(nickname);
        if(player == null) channel.sendPacket(PacketId.RequestedNicknameNotExists, Unpooled.buffer());
        else channel.sendPacket(PacketId.RequestedNicknameExists, Unpooled.buffer());
    }
}
