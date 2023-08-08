package ua.lann.protankiserver.game.localization;

import com.squareup.moshi.Types;
import ua.lann.protankiserver.serialization.JsonUtils;

import java.util.HashMap;
import java.util.Map;

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
        Map<String, GarageItemsLocalizationModelItem> obj = JsonUtils.readResource(resourceName, Types.newParameterizedType(
            Map.class,
            String.class,
            GarageItemsLocalizationModelItem.class
        ));

        for(String key : obj.keySet()) {
            GarageItemLocalizedData data = mapping.get(key);
            if(data == null) data = new GarageItemLocalizedData();

            GarageItemsLocalizationModelItem raw = obj.get(key);

            data.setName(locale, raw.getName());
            data.setDescription(locale, raw.getDescription());

            mapping.put(key, data);
        }
    }
}
