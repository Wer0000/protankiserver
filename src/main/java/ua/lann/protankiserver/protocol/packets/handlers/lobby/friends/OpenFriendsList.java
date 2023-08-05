package ua.lann.protankiserver.protocol.packets.handlers.lobby.friends;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import ua.lann.protankiserver.ClientController;
import ua.lann.protankiserver.protocol.packets.PacketId;
import ua.lann.protankiserver.protocol.packets.handlers.IHandler;

public class OpenFriendsList implements IHandler {
    @Override
    public void handle(ClientController channel, ByteBuf buf) {
        channel.sendPacket(PacketId.SetFriendsListOpened, Unpooled.buffer());
    }
}
