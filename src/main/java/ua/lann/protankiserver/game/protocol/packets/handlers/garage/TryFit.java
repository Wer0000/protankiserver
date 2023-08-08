package ua.lann.protankiserver.game.protocol.packets.handlers.garage;

import io.netty.buffer.ByteBuf;
import ua.lann.protankiserver.ClientController;
import ua.lann.protankiserver.game.garage.GarageManager;
import ua.lann.protankiserver.game.protocol.packets.CodecRegistry;
import ua.lann.protankiserver.game.protocol.packets.PacketId;
import ua.lann.protankiserver.game.protocol.packets.handlers.IHandler;
import ua.lann.protankiserver.reflection.annotations.PacketHandler;

@PacketHandler(packetId = PacketId.TryFit)
public class TryFit implements IHandler {
    @Override
    public void handle(ClientController channel, ByteBuf buf) {
        String item = CodecRegistry.getCodec(String.class).decode(buf);
        GarageManager.tryFitEquip(channel, item);
    }
}
