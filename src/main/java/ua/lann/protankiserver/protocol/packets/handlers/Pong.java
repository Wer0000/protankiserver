package ua.lann.protankiserver.protocol.packets.handlers;

import io.netty.buffer.ByteBuf;
import ua.lann.protankiserver.ClientController;

public class Pong implements IHandler {
    @Override
    public void handle(ClientController channel, ByteBuf buf) {}
}
