package ua.lann.protankiserver.game.protocol.packets.handlers.lobby;

import com.google.gson.JsonObject;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import ua.lann.protankiserver.ClientController;
import ua.lann.protankiserver.reflection.annotations.PacketHandler;
import ua.lann.protankiserver.game.battles.BattleBase;
import ua.lann.protankiserver.game.battles.BattlesManager;
import ua.lann.protankiserver.models.battle.BattleDisplayInfo;
import ua.lann.protankiserver.game.protocol.packets.CodecRegistry;
import ua.lann.protankiserver.game.protocol.packets.PacketId;
import ua.lann.protankiserver.game.protocol.packets.codec.ICodec;
import ua.lann.protankiserver.game.protocol.packets.handlers.IHandler;
import ua.lann.protankiserver.util.JsonUtils;

@PacketHandler(packetId = PacketId.GetBattleInfo)
public class GetBattleInfo implements IHandler {
    @Override
    public void handle(ClientController channel, ByteBuf buf) {
        ICodec<String> stringICodec = CodecRegistry.getCodec(String.class);
        ICodec<JsonObject> objectICodec = CodecRegistry.getCodec(JsonObject.class);

        String battleId = stringICodec.decode(buf);

        ByteBuf buffer = Unpooled.buffer();
        stringICodec.encode(buffer, battleId);

        channel.sendPacket(PacketId.SetBattleSelected, buffer);
        buffer.clear();

        BattleBase battle = BattlesManager.getBattle(battleId);
        if(battle == null) return;

        // TODO: battle.addViewer

        BattleDisplayInfo displayInfo = battle.getBattleDisplayInfo();

        objectICodec.encode(buffer, JsonUtils.toJsonObject(displayInfo));

        channel.sendPacket(PacketId.SendBattleInfo, buffer);
        buffer.release();
    }
}
