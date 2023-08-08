package ua.lann.protankiserver.game.localization;

import com.squareup.moshi.Json;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class GarageItemsLocalizationModelItem {
    @Json private String name;
    @Json private String description;
}
