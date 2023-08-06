package ua.lann.protankiserver.protocol.packets.handlers.lobby.friends;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import ua.lann.protankiserver.ClientController;
import ua.lann.protankiserver.reflection.annotations.PacketHandler;
import ua.lann.protankiserver.orm.entities.FriendRequest;
import ua.lann.protankiserver.orm.entities.Player;
import ua.lann.protankiserver.protocol.packets.CodecRegistry;
import ua.lann.protankiserver.protocol.packets.PacketId;
import ua.lann.protankiserver.protocol.packets.codec.ICodec;
import ua.lann.protankiserver.protocol.packets.handlers.IHandler;

import java.util.Optional;

@PacketHandler(packetId = PacketId.SendFriendRequest)
public class SendFriendRequest implements IHandler {
    @Override
    public void handle(ClientController channel, ByteBuf buf) {
        ICodec<String> stringICodec = CodecRegistry.getCodec(String.class);
        String nickname = stringICodec.decode(buf);

        ByteBuf buffer = Unpooled.buffer();
        try {
            Player player = channel.getPlayer();
            if(player.getFriends().stream().anyMatch(x -> x.getNickname() == null)) {
                stringICodec.encode(buffer, nickname);
                channel.sendPacket(PacketId.AlreadyFriends, buffer);
                return;
            }

            Optional<FriendRequest> requestIn = player.getIncomingFriendRequests().stream().filter(x -> x.getSender().getNickname().equals(nickname)).findFirst();
            if(requestIn.isPresent()) {
                stringICodec.encode(buffer, nickname);
                channel.sendPacket(PacketId.AlreadyHasIncomingRequest, buffer);
                return;
            }

            boolean requestOut = player.getOutgoingFriendRequests().stream().anyMatch(x -> x.getReceiver().getNickname().equals(nickname));
            if(requestOut) {
                stringICodec.encode(buffer, nickname);
                channel.sendPacket(PacketId.AlreadySentFriendRequestToThatPlayer, buffer);
                return;
            }

            if(player.getFriends().stream().noneMatch(x -> x.getNickname().equals(nickname))) {
                stringICodec.encode(buffer, nickname);
                boolean success = channel.getFriendsManager().sendFriendRequest(nickname);
                if(!success) channel.alert("Failed to send friend request, try again later");
                else channel.sendPacket(PacketId.RequestSent, buffer);
            }
        } finally {
            buffer.release();
        }
    }
}
