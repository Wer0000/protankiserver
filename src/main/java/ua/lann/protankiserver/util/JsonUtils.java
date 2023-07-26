package ua.lann.protankiserver.util;

import com.google.gson.*;

import java.io.InputStream;
import java.io.InputStreamReader;

public class JsonUtils {
    private static final Gson gson = new Gson();

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
