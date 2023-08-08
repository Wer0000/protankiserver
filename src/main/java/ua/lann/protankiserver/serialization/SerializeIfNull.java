package ua.lann.protankiserver.serialization;

import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.JsonQualifier;
import com.squareup.moshi.Moshi;
import com.squareup.moshi.Types;
import ua.lann.protankiserver.serialization.adapters.NullIfNullJsonAdapter;

import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.reflect.Type;
import java.util.Set;

@Retention(RetentionPolicy.RUNTIME)
@JsonQualifier
public @interface SerializeIfNull {
    class JSON_ADAPTER_FACTORY implements JsonAdapter.Factory {
        @Override
        public JsonAdapter<?> create(Type type, Set<? extends Annotation> annotations, Moshi moshi) {
            Set<? extends Annotation> nextAnnotations = Types.nextAnnotations(annotations, SerializeIfNull.class);
            if (nextAnnotations == null) {
                return null;
            }

            return new NullIfNullJsonAdapter<>(moshi.nextAdapter(this, type, nextAnnotations));
        }
    }
}