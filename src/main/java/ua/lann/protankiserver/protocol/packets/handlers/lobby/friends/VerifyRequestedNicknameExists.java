package ua.lann.protankiserver.protocol.packets.handlers.lobby.friends;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import org.hibernate.Session;
import ua.lann.protankiserver.ClientController;
import ua.lann.protankiserver.orm.HibernateUtils;
import ua.lann.protankiserver.orm.entities.Player;
import ua.lann.protankiserver.protocol.packets.CodecRegistry;
import ua.lann.protankiserver.protocol.packets.PacketId;
import ua.lann.protankiserver.protocol.packets.codec.ICodec;
import ua.lann.protankiserver.protocol.packets.handlers.IHandler;

public class VerifyRequestedNicknameExists implements IHandler {
    @Override
    public void handle(ClientController channel, ByteBuf buf) {
        ICodec<String> stringICodec = CodecRegistry.getCodec(String.class);
        String nickname = stringICodec.decode(buf);

        if(nickname.equals(channel.getProfile().getNickname())) {
            channel.sendPacket(PacketId.RequestedNicknameNotExists, Unpooled.buffer());
            return;
        }

        try(Session session = HibernateUtils.session()) {
            Player player = session.get(Player.class, nickname);
            if(player == null) channel.sendPacket(PacketId.RequestedNicknameNotExists, Unpooled.buffer());
            else channel.sendPacket(PacketId.RequestedNicknameExists, Unpooled.buffer());
        }
    }
}
