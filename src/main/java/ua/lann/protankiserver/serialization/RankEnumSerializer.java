package ua.lann.protankiserver.serialization;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import ua.lann.protankiserver.enums.Rank;

import java.lang.reflect.Type;

public class RankEnumSerializer implements JsonSerializer<Rank> {
    @Override
    public JsonElement serialize(Rank rank, Type type, JsonSerializationContext jsonSerializationContext) {
        return new JsonPrimitive(rank.getNumber());
    }
}
