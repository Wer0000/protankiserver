package ua.lann.protankiserver.serialization;

import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;
import lombok.Getter;
import ua.lann.protankiserver.serialization.adapters.GarageItemTypeAdapter;
import ua.lann.protankiserver.serialization.adapters.RankAdapter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.ParameterizedType;
import java.util.stream.Collectors;

public class JsonUtils {
    @Getter
    private static final Moshi moshi = new Moshi.Builder()
        .add(new RankAdapter())
        .add(new GarageItemTypeAdapter())
        .add(new SerializeIfNull.JSON_ADAPTER_FACTORY())
        .build();

    public static <T> T readResource(String path, ParameterizedType clazz) {
        try(InputStream stream = JsonUtils.class.getClassLoader().getResourceAsStream(path)) {
            JsonAdapter<T> adapter = moshi.adapter(clazz);

            String jsonContent = new BufferedReader(new InputStreamReader(stream))
                .lines().collect(Collectors.joining("\n"));

            return adapter.fromJson(jsonContent);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static <T> T readResource(String path, Class<T> clazz) {
        try(InputStream stream = JsonUtils.class.getClassLoader().getResourceAsStream(path)) {
            JsonAdapter<T> adapter = moshi.adapter(clazz);

            String jsonContent = new BufferedReader(new InputStreamReader(stream))
                .lines().collect(Collectors.joining("\n"));

            return adapter.fromJson(jsonContent);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static <T> String toString(T object, Class<T> clazz) {
        JsonAdapter<T> adapter = moshi.adapter(clazz);
        return adapter.toJson(object);
    }
}
