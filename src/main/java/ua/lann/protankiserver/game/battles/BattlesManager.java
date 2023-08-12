package ua.lann.protankiserver.game.battles;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import lombok.Getter;
import ua.lann.protankiserver.ClientController;
import ua.lann.protankiserver.enums.BattleMode;
import ua.lann.protankiserver.game.battles.models.InitBattlesListModel;
import ua.lann.protankiserver.models.battle.BattleLimit;
import ua.lann.protankiserver.game.protocol.packets.CodecRegistry;
import ua.lann.protankiserver.game.protocol.packets.PacketId;
import ua.lann.protankiserver.serialization.JsonUtils;

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

    public static List<BattleBase> getBattles() { return battles.values().stream().toList(); }

    public static BattleBase getBattle(String battleId) {
        return battles.get(battleId);
    }
    public static void addBattle(BattleBase battle) { battles.put(battle.getId(), battle); }

    public static void sendBattlesList(ClientController controller) {
        ByteBuf buf = Unpooled.buffer();

        InitBattlesListModel model = new InitBattlesListModel();
        model.setBattles(battles.values().stream().map(BattleBase::getBattleListInfo).toList());

        CodecRegistry.getCodec(String.class).encode(buf, JsonUtils.toString(model, InitBattlesListModel.class));

        controller.sendPacket(PacketId.InitBattleList, buf);
        buf.release();
    }
}
