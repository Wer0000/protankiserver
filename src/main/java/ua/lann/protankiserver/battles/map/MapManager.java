package ua.lann.protankiserver.battles.map;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.lann.protankiserver.ClientController;
import ua.lann.protankiserver.models.battle.BattleLimit;
import ua.lann.protankiserver.battles.BattlesManager;
import ua.lann.protankiserver.enums.BattleMode;
import ua.lann.protankiserver.enums.MapTheme;
import ua.lann.protankiserver.protocol.packets.CodecRegistry;
import ua.lann.protankiserver.protocol.packets.PacketId;
import ua.lann.protankiserver.protocol.packets.codec.ICodec;
import ua.lann.protankiserver.util.JsonUtils;

import java.util.ArrayList;
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

        JsonArray mapsArray = new JsonArray();
        for(Map map : enabledMaps) mapsArray.add(map.toJsonObject(controller.getLocale()));

        JsonObject object = new JsonObject();
        JsonArray battleLimits = new JsonArray();
        for(BattleLimit limit : BattlesManager.getLimits()) battleLimits.add(JsonUtils.toJsonObject(limit));

        object.addProperty("maxRangleLength", 10);
        object.addProperty("battleCreationDisabled", controller.getPlayer().getRank().getNumber() == 1);
        object.add("battleLimits", battleLimits);
        object.add("maps", mapsArray);

        ICodec<JsonObject> objectICodec = CodecRegistry.getCodec(JsonObject.class);
        objectICodec.encode(buf, object);

        controller.sendPacket(PacketId.InitMapList, buf);
        buf.release();
    }

    public static void loadMaps() {
        JsonArray array = JsonUtils.readJsonArray("maps.json");
        for(JsonElement element : array) {
            JsonObject obj = element.getAsJsonObject();

            List<BattleMode> modes = new ArrayList<>();
            JsonArray supModes = obj.get("supportedModes").getAsJsonArray();
            MapTheme theme = MapTheme.fromString(obj.get("theme").getAsString());

            for(JsonElement mode : supModes) modes.add(BattleMode.valueOf(mode.getAsString()));

            Map map = new Map(
                obj.get("enabled").getAsBoolean(),
                obj.get("mapId").getAsString(),
                obj.get("maxPeople").getAsInt(),
                obj.get("preview").getAsInt(),
                obj.get("maxRank").getAsInt(),
                obj.get("minRank").getAsInt(),
                    modes, theme
            );

            maps.put(map.getMapId(), map);
        }

        logger.info("Loaded {} maps", maps.size());
    }
}
