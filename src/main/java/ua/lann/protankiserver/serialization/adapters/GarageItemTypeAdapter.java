package ua.lann.protankiserver.serialization.adapters;

import com.squareup.moshi.FromJson;
import com.squareup.moshi.ToJson;
import ua.lann.protankiserver.enums.GarageItemType;

public class GarageItemTypeAdapter {
    @ToJson public int toJson(GarageItemType type) {
        return type.getKey();
    }
    @FromJson public GarageItemType fromJson(int json) {
        return GarageItemType.fromKey(json);
    }
}
