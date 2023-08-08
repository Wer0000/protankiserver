package ua.lann.protankiserver.game.battles.models;

import com.squareup.moshi.Json;
import lombok.AllArgsConstructor;
import lombok.Getter;
import ua.lann.protankiserver.enums.BattleMode;
import ua.lann.protankiserver.enums.MapTheme;
import ua.lann.protankiserver.game.localization.Locale;
import ua.lann.protankiserver.game.localization.MapNames;

import java.util.List;

@Getter
@AllArgsConstructor
public class Map {
    @Json(name = "enabled") private final boolean isEnabled;
    @Json private final String mapId;
    @Json private final int maxPeople;
    @Json(name = "preview") private final int previewResourceId;
    @Json private final int maxRank;
    @Json private final int minRank;
    @Json private final List<BattleMode> supportedModes;
    @Json private final MapTheme theme;

    public String getLocalizedName(Locale locale) {
        return MapNames.getName(mapId, locale);
    }
}
