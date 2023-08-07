package ua.lann.protankiserver.util;

import com.google.gson.*;
import ua.lann.protankiserver.enums.GarageItemType;
import ua.lann.protankiserver.enums.Rank;
import ua.lann.protankiserver.serialization.GarageItemTypeSerializer;
import ua.lann.protankiserver.serialization.RankEnumSerializer;
import ua.lann.protankiserver.serialization.SerializableAsNullConverter;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

public class JsonUtils {
    private static final Gson gson = new GsonBuilder()
            .registerTypeAdapterFactory(new SerializableAsNullConverter())
            .registerTypeAdapter(Rank.class, new RankEnumSerializer())
            .registerTypeAdapter(GarageItemType.class, new GarageItemTypeSerializer())
            .create();

    public static JsonObject toJsonObject(Object instance) {
        String jsonStr = gson.toJson(instance);
        JsonElement jsonElement = gson.fromJson(jsonStr, JsonElement.class);
        if (jsonElement.isJsonObject()) {
            return jsonElement.getAsJsonObject();
        } else {
            // Handle other JSON types if needed
            throw new IllegalArgumentException("Input is not a JSON object.");
        }
    }

    public static Object create(JsonObject obj, Class<?> clazz) {
        return gson.fromJson(obj, clazz);
    }

    public static JsonArray toJsonArray(List<Object> list) {
        JsonArray array = new JsonArray();
        for(Object obj : list) array.add(toJsonObject(obj));
        return array;
    }

    public static JsonArray readJsonArray(String resourceName) {
        InputStream stream = JsonUtils.class.getClassLoader().getResourceAsStream(resourceName);
        if(stream == null) return null;

        JsonElement element = JsonParser.parseReader(new InputStreamReader(stream));
        return element.getAsJsonArray();
    }

    public static JsonObject readJsonObject(String resourceName) {
        InputStream stream = JsonUtils.class.getClassLoader().getResourceAsStream(resourceName);
        if(stream == null) return null;

        JsonElement element = JsonParser.parseReader(new InputStreamReader(stream));
        return element.getAsJsonObject();
    }


    public static String toString(Object object) {
        return gson.toJson(object);
    }
}
