package ua.lann.protankiserver.game.localization;

import com.google.gson.JsonObject;
import ua.lann.protankiserver.util.JsonUtils;

import java.util.HashMap;

public class GarageItemsLocalization {
    private static final HashMap<String, GarageItemLocalizedData> mapping = new HashMap<>();

    public static void load() {
        load(Locale.Russian, "localization/ru/garageItems.json");
        load(Locale.English, "localization/en/garageItems.json");
    }

    public static GarageItemLocalizedData getData(String itemId) {
        return mapping.get(itemId);
    }

    private static void load(Locale locale, String resourceName) {
        JsonObject obj = JsonUtils.readJsonObject(resourceName);
        for(String key : obj.keySet()) {
            JsonObject _obj = obj.get(key).getAsJsonObject();
            GarageItemLocalizedData data = mapping.get(key);
            if(data == null) data = new GarageItemLocalizedData();

            data.setName(locale, _obj.get("name").getAsString());
            data.setDescription(locale, _obj.get("description").getAsString());

            mapping.put(key, data);
        }
    }
}
