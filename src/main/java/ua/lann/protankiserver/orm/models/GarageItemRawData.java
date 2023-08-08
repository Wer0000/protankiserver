package ua.lann.protankiserver.orm.models;

import com.squareup.moshi.Json;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
public class GarageItemRawData {
    @Json(name = "propers") Map<String, GarageItemRawProperty> properties;
    @Json int object3ds;
    @Json int baseItemId;
    @Json int previewResourceId;
    // sfx data
}
