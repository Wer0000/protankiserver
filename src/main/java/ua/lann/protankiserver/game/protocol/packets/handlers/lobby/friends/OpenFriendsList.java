package ua.lann.protankiserver.game.protocol.packets.handlers.lobby.friends;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import ua.lann.protankiserver.ClientController;
import ua.lann.protankiserver.reflection.annotations.PacketHandler;
import ua.lann.protankiserver.game.protocol.packets.PacketId;
import ua.lann.protankiserver.game.protocol.packets.handlers.IHandler;

@PacketHandler(packetId = PacketId.OpenFriendsList)
public class OpenFriendsList implements IHandler {
    @Override
    public void handle(ClientController channel, ByteBuf buf) {
        channel.sendPacket(PacketId.SetFriendsListOpened, Unpooled.buffer());
    }
}
