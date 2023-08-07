package ua.lann.protankiserver.serialization;

import com.google.gson.*;
import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import ua.lann.protankiserver.reflection.annotations.SerializeIfNull;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class SerializableAsNullConverter implements TypeAdapterFactory {

    @Override
    public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> type) {
        List<String> nullableFieldNames = new ArrayList<>();
        List<String> nonNullableFields = new ArrayList<>();

        for (Field declaredField : type.getRawType().getDeclaredFields()) {
            if (declaredField.getAnnotationsByType(SerializeIfNull.class).length > 0) {
                nullableFieldNames.add(getSerializedName(declaredField));
            }
            nonNullableFields.add(getSerializedName(declaredField));
        }

        nonNullableFields.removeAll(nullableFieldNames);

        if (nullableFieldNames.isEmpty()) {
            return null;
        } else {
            TypeAdapter<T> delegateAdapter = gson.getDelegateAdapter(this, type);
            TypeAdapter<JsonElement> elementAdapter = gson.getAdapter(JsonElement.class);

            return new TypeAdapter<>() {
                @Override
                public void write(JsonWriter writer, T value) throws IOException {
                    JsonObject jsonObject = delegateAdapter.toJsonTree(value).getAsJsonObject();

                    for (String fieldName : nonNullableFields) {
                        if (jsonObject.get(fieldName).isJsonNull()) {
                            jsonObject.remove(fieldName);
                        }
                    }

                    boolean originalSerializeNulls = writer.getSerializeNulls();
                    writer.setSerializeNulls(true);
                    elementAdapter.write(writer, jsonObject);
                    writer.setSerializeNulls(originalSerializeNulls);
                }

                @Override
                public T read(JsonReader reader) throws IOException {
                    return delegateAdapter.read(reader);
                }
            };
        }
    }

    private String getSerializedName(Field field) {
        SerializedName annotation = field.getAnnotation(SerializedName.class);
        if (annotation != null) {
            return annotation.value();
        } else {
            return field.getName();
        }
    }
}