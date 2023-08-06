package ua.lann.protankiserver.protocol.packets.handlers.base;

import io.netty.buffer.ByteBuf;
import ua.lann.protankiserver.ClientController;
import ua.lann.protankiserver.reflection.annotations.PacketHandler;
import ua.lann.protankiserver.protocol.packets.PacketId;
import ua.lann.protankiserver.protocol.packets.handlers.IHandler;


@PacketHandler(packetId = PacketId.Pong)
public class Pong implements IHandler {
    @Override
    public void handle(ClientController channel, ByteBuf buf) {}
}
