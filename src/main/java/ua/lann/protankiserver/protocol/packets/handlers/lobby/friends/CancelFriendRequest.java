package ua.lann.protankiserver.protocol.packets.handlers.lobby.friends;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import org.hibernate.Session;
import ua.lann.protankiserver.ClientController;
import ua.lann.protankiserver.Server;
import ua.lann.protankiserver.reflection.annotations.PacketHandler;
import ua.lann.protankiserver.orm.HibernateUtils;
import ua.lann.protankiserver.orm.entities.FriendRequest;
import ua.lann.protankiserver.orm.entities.Player;
import ua.lann.protankiserver.protocol.packets.CodecRegistry;
import ua.lann.protankiserver.protocol.packets.PacketId;
import ua.lann.protankiserver.protocol.packets.codec.ICodec;
import ua.lann.protankiserver.protocol.packets.handlers.IHandler;

import java.util.Optional;

@PacketHandler(packetId = PacketId.CancelFriendRequest)
public class CancelFriendRequest implements IHandler {
    @Override
    public void handle(ClientController channel, ByteBuf buf) {
        ICodec<String> stringICodec = CodecRegistry.getCodec(String.class);
        String nickname = stringICodec.decode(buf);

        try(Session session = HibernateUtils.session()) {
            Player player = channel.getPlayer();
            Optional<FriendRequest> optionalRequest = player.getOutgoingFriendRequests().stream().filter(x -> x.getReceiver().getNickname().equals(nickname)).findFirst();
            if(optionalRequest.isEmpty()) return;

            FriendRequest request = optionalRequest.get();

            Player targetPlayer = session.get(Player.class, nickname);
            player.getOutgoingFriendRequests().remove(request);
            targetPlayer.getIncomingFriendRequests().remove(request);

            session.beginTransaction();
            session.remove(request);
            session.getTransaction().commit();

            session.beginTransaction();
            session.merge(player);
            session.merge(targetPlayer);
            session.getTransaction().commit();

            ClientController targetController = Server.getInstance().tryGetOnlinePlayerController(nickname);
            if(targetController == null) return;

            ByteBuf buffer = Unpooled.buffer();
            stringICodec.encode(buffer, channel.getPlayer().getNickname());
            targetController.sendPacket(PacketId.NotifyFriendRequestCancelled, buffer);
            buffer.release();
        }
    }
}
