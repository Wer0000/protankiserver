package ua.lann.protankiserver.game.protocol.packets.handlers.garage;

import io.netty.buffer.ByteBuf;
import ua.lann.protankiserver.ClientController;
import ua.lann.protankiserver.game.protocol.packets.CodecRegistry;
import ua.lann.protankiserver.game.protocol.packets.PacketId;
import ua.lann.protankiserver.game.protocol.packets.codec.ICodec;
import ua.lann.protankiserver.game.protocol.packets.handlers.IHandler;
import ua.lann.protankiserver.reflection.annotations.PacketHandler;

@PacketHandler(packetId = PacketId.BuyItem)
public class BuyItem implements IHandler {
    @Override
    public void handle(ClientController channel, ByteBuf buf) {
        ICodec<String> stringICodec = CodecRegistry.getCodec(String.class);
        String item = stringICodec.decode(buf);
        String itemId = item.split("_m")[0];
        String modification = item.split("_m")[1];

        int amount = buf.readInt();
        int value = buf.readInt();

        channel.getGarageManager().buyItem(itemId, Integer.parseInt(modification), amount, value);
    }
}
