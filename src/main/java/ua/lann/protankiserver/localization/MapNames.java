package ua.lann.protankiserver.localization;

import com.google.gson.JsonObject;
import ua.lann.protankiserver.util.JsonUtils;

import java.util.HashMap;

public class MapNames {
    private static final HashMap<String, String> mappingRu;
    private static final HashMap<String, String> mappingEn;

    static {
        mappingRu = new HashMap<>();
        mappingEn = new HashMap<>();

        load(mappingRu, "localization/mapNames_ru.json");
        load(mappingEn, "localization/mapNames_en.json");
    }

    public static String getName(String mapId, Locale locale) {
         return locale.equals(Locale.English) ? mappingEn.get(mapId) : mappingRu.get(mapId);
    }

    private static void load(HashMap<String, String> destination, String resourceName) {
        JsonObject obj = JsonUtils.readJsonObject(resourceName);
        for(String key : obj.keySet()) {
            destination.put(key, obj.get(key).getAsString());
        }
    }
}
