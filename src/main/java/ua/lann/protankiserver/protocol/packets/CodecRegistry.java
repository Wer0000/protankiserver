package ua.lann.protankiserver.protocol.packets;

import com.google.gson.JsonObject;
import ua.lann.protankiserver.protocol.packets.codec.ICodec;
import ua.lann.protankiserver.protocol.packets.codec.JsonObjectCodec;
import ua.lann.protankiserver.protocol.packets.codec.UTFStringCodec;
import ua.lann.protankiserver.protocol.packets.codec.primitive.BooleanCodec;

import java.util.HashMap;
import java.util.Map;

public class CodecRegistry {
    private static final Map<Class<?>, ICodec<?>> registry = new HashMap<>();

    static {
        registerCodec(Boolean.class, new BooleanCodec());

        registerCodec(String.class, new UTFStringCodec());
        registerCodec(JsonObject.class, new JsonObjectCodec());
    }

    public static <T> void registerCodec(Class<T> alias, ICodec<T> codec) {
        registry.put(alias, codec);
    }

    @SuppressWarnings("unchecked")
    public static <T> ICodec<T> getCodec(Class<T> alias) {
        return (ICodec<T>) registry.get(alias);
    }
}