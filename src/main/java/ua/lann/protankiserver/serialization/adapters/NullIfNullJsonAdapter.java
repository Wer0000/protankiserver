package ua.lann.protankiserver.serialization.adapters;

import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.JsonReader;
import com.squareup.moshi.JsonWriter;

import java.io.IOException;

public class NullIfNullJsonAdapter<T> extends JsonAdapter<T> {
    private final JsonAdapter<T> delegate;

    public NullIfNullJsonAdapter(JsonAdapter<T> delegate) {
        this.delegate = delegate;
    }

    @Override
    public T fromJson(JsonReader reader) throws IOException {
        return delegate.fromJson(reader);
    }

    @Override
    public void toJson(JsonWriter writer, T value) throws IOException {
        if (value == null) {
            boolean serializeNulls = writer.getSerializeNulls();
            writer.setSerializeNulls(true);
            try {
                delegate.toJson(writer, value);
            } finally {
                writer.setSerializeNulls(serializeNulls);
            }
        } else {
            delegate.toJson(writer, value);
        }
    }

    @Override
    public String toString() {
        return delegate + ".serializeNulls()";
    }
}