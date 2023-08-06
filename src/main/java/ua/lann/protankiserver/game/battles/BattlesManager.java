package ua.lann.protankiserver.game.battles;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import lombok.Getter;
import ua.lann.protankiserver.ClientController;
import ua.lann.protankiserver.enums.BattleMode;
import ua.lann.protankiserver.models.battle.BattleLimit;
import ua.lann.protankiserver.game.protocol.packets.CodecRegistry;
import ua.lann.protankiserver.game.protocol.packets.PacketId;
import ua.lann.protankiserver.game.protocol.packets.codec.ICodec;
import ua.lann.protankiserver.util.JsonUtils;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class BattlesManager {
    @Getter private static final List<BattleLimit> limits = Arrays.asList(
        new BattleLimit(BattleMode.DM, 999, 59940),
        new BattleLimit(BattleMode.TDM, 999, 59940),
        new BattleLimit(BattleMode.CTF, 999, 59940),
        new BattleLimit(BattleMode.CP, 999, 59940),
        new BattleLimit(BattleMode.AS, 999, 59940)
    );

    private static final HashMap<String, BattleBase> battles = new HashMap<>();

    public static BattleBase getBattle(String battleId) {
        return battles.get(battleId);
    }
    public static void addBattle(BattleBase battle) { battles.put(battle.getId(), battle); }

    public static void sendBattlesList(ClientController controller) {
        ByteBuf buf = Unpooled.buffer();

        JsonArray battlesArray = new JsonArray();
        for(BattleBase battle : battles.values()) battlesArray.add(JsonUtils.toJsonObject(battle.getBattleListInfo()));

        JsonObject object = new JsonObject();
        object.add("battles", battlesArray);

        ICodec<JsonObject> objectICodec = CodecRegistry.getCodec(JsonObject.class);
        objectICodec.encode(buf, object);

        controller.sendPacket(PacketId.InitBattleList, buf);
        buf.release();
    }
}
