package ua.lann.protankiserver.protocol.packets.handlers.lobby.friends;

import io.netty.buffer.ByteBuf;
import org.hibernate.Session;
import ua.lann.protankiserver.ClientController;
import ua.lann.protankiserver.FriendsManager;
import ua.lann.protankiserver.orm.HibernateUtils;
import ua.lann.protankiserver.orm.entities.FriendRequest;
import ua.lann.protankiserver.orm.entities.Player;
import ua.lann.protankiserver.protocol.packets.CodecRegistry;
import ua.lann.protankiserver.protocol.packets.codec.ICodec;
import ua.lann.protankiserver.protocol.packets.handlers.IHandler;

import java.util.Optional;

public class RejectFriendRequest implements IHandler {
    @Override
    public void handle(ClientController channel, ByteBuf buf) {
        ICodec<String> stringICodec = CodecRegistry.getCodec(String.class);
        String nickname = stringICodec.decode(buf);

        try(Session session = HibernateUtils.session()) {
            Player player = session.get(Player.class, channel.getProfile().getNickname());
            Optional<FriendRequest> optionalRequest = player.getOutgoingFriendRequests().stream().filter(x -> x.getReceiver().getNickname().equals(nickname)).findFirst();
            if (optionalRequest.isEmpty()) return;

            FriendRequest request = optionalRequest.get();
            FriendsManager.rejectFriendRequest(session, request);
        }
    }
}
