package ua.lann.protankiserver.serialization;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import ua.lann.protankiserver.enums.GarageItemType;

import java.lang.reflect.Type;

public class GarageItemTypeSerializer implements JsonSerializer<GarageItemType> {
    @Override
    public JsonElement serialize(GarageItemType garageItemType, Type type, JsonSerializationContext jsonSerializationContext) {
        return new JsonPrimitive(garageItemType.getKey());
    }
}
