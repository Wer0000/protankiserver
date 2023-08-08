package ua.lann.protankiserver.orm.models;

import com.squareup.moshi.Json;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@Getter @Setter
public class GarageItemData {
    private int modification;

    @Json(name = "propers") List<GarageItemProperty> properties;
    @Json int object3ds;
    @Json int baseItemId;
    @Json int previewResourceId;
    // sfx data
}
