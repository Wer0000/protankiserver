package ua.lann.protankiserver.orm.models;

import com.squareup.moshi.Json;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ua.lann.protankiserver.serialization.SerializeIfNull;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GarageItemRawProperty {
    @Json @SerializeIfNull Object value;
    @Json @SerializeIfNull List<String> subproperties;
}
