package ua.lann.protankiserver.game.localization;

import com.squareup.moshi.JsonClass;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Map;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class GarageItemsLocalizationModel {
    private Map<String, GarageItemsLocalizationModelItem> keyMap;
}
