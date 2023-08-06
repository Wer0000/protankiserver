package ua.lann.protankiserver.protocol.packets.handlers.base;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import org.hibernate.Session;
import ua.lann.protankiserver.ClientController;
import ua.lann.protankiserver.Server;
import ua.lann.protankiserver.reflection.annotations.PacketHandler;
import ua.lann.protankiserver.orm.HibernateUtils;
import ua.lann.protankiserver.orm.entities.Player;
import ua.lann.protankiserver.protocol.packets.CodecRegistry;
import ua.lann.protankiserver.protocol.packets.PacketId;
import ua.lann.protankiserver.protocol.packets.codec.ICodec;
import ua.lann.protankiserver.protocol.packets.handlers.IHandler;

@PacketHandler(packetId = PacketId.RequestUserInfo)
public class RequestUserInfo implements IHandler {
    @Override
    public void handle(ClientController channel, ByteBuf buf) {
        ICodec<String> stringICodec = CodecRegistry.getCodec(String.class);
        ICodec<Boolean> booleanICodec = CodecRegistry.getCodec(Boolean.class);
        String nickname = stringICodec.decode(buf);

        ClientController targetPlayerController = Server.getInstance().tryGetOnlinePlayerController(nickname);
        Player targetPlayer = null;

        if(targetPlayerController == null) {
            try(Session session = HibernateUtils.session()) {
                targetPlayer = session.get(Player.class, nickname);
            }
        } else targetPlayer = targetPlayerController.getPlayer();

        if(targetPlayer == null) return;

        ByteBuf buffer = Unpooled.buffer();

        booleanICodec.encode(buffer, Server.getInstance().tryGetOnlinePlayerController(nickname) != null); // Is player online
        buffer.writeInt(1); // server id
        stringICodec.encode(buffer, targetPlayer.getNickname());

        channel.sendPacket(PacketId.SetUserOnlineInfo, buffer);
        buffer.clear();

        buffer.writeInt(targetPlayer.getRank().getNumber());
        stringICodec.encode(buffer, targetPlayer.getNickname());

        channel.sendPacket(PacketId.SetUserRankInfo, buffer);
        buffer.clear();

        stringICodec.encode(buffer, targetPlayer.getNickname());
        channel.sendPacket(PacketId.NotifySomethingIDontKnow, buffer); // IDK lol, if you know what packet 1941694508 does open issue and let me know
        buffer.clear();

        buffer.writeInt(32989); // TODO: Premium left in seconds
        stringICodec.encode(buffer, targetPlayer.getNickname());
        channel.sendPacket(PacketId.SetUserPremiumInfo, buffer);

        buffer.release();
    }
}
