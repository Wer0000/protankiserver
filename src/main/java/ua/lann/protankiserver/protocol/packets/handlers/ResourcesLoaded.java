package ua.lann.protankiserver.protocol.packets.handlers;

import io.netty.buffer.ByteBuf;
import ua.lann.protankiserver.ClientController;

public class ResourcesLoaded implements IHandler {
    @Override
    public void handle(ClientController channel, ByteBuf buf) {
        channel.resources.notifyLoaded(buf.readInt());
    }
}
