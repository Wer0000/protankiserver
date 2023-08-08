package ua.lann.protankiserver.game.battles;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.lann.protankiserver.ClientController;
import ua.lann.protankiserver.game.battles.models.InitMapListModel;
import ua.lann.protankiserver.game.battles.models.Map;
import ua.lann.protankiserver.game.protocol.packets.CodecRegistry;
import ua.lann.protankiserver.game.protocol.packets.PacketId;
import ua.lann.protankiserver.game.protocol.packets.codec.ICodec;
import ua.lann.protankiserver.serialization.JsonUtils;

import java.util.HashMap;
import java.util.List;

public class MapManager {
    private static final Logger logger = LoggerFactory.getLogger(MapManager.class);
    private static final HashMap<String, Map> maps = new HashMap<>();

    public static Map getMap(String id) {
        return maps.get(id);
    }

    public static void sendMapList(ClientController controller) {
        ByteBuf buf = Unpooled.buffer();

        List<Map> enabledMaps = maps.values().stream()
                .filter(Map::isEnabled).toList();

        InitMapListModel model = new InitMapListModel();
        model.setMaxRangeLength(10);
        model.setBattleCreationDisabled(controller.getPlayer().getRank().getNumber() == 1);
        model.setBattleLimits(BattlesManager.getLimits());
        model.setMaps(enabledMaps);

        ICodec<String> stringICodec = CodecRegistry.getCodec(String.class);
        stringICodec.encode(buf, JsonUtils.toString(model, InitMapListModel.class));

        controller.sendPacket(PacketId.InitMapList, buf);
        buf.release();
    }

    public static void loadMaps() {
        Map[] array = JsonUtils.readResource("maps.json", Map[].class);
        for(Map map : array) maps.put(map.getMapId(), map);

        logger.info("Loaded {} maps", maps.size());
    }
}
