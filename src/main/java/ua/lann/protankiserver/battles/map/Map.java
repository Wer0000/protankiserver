package ua.lann.protankiserver.battles.map;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import lombok.AllArgsConstructor;
import lombok.Getter;
import ua.lann.protankiserver.enums.BattleMode;
import ua.lann.protankiserver.enums.MapTheme;
import ua.lann.protankiserver.localization.Locale;
import ua.lann.protankiserver.localization.MapNames;

import java.util.List;

@AllArgsConstructor
@Getter
public class Map {
    private final boolean isEnabled;
    private final String mapId;
    private final int maxPeople;
    private final int previewResourceId;
    private final int maxRank;
    private final int minRank;
    private final List<BattleMode> supportedModes;
    private final MapTheme theme;

    public JsonObject toJsonObject(Locale locale) {
        JsonArray supportedModesList = new JsonArray();
        for(BattleMode mode : supportedModes) supportedModesList.add(mode.toString());

        JsonObject obj = new JsonObject();
        obj.addProperty("enabled", isEnabled);
        obj.addProperty("mapId", mapId);
        obj.addProperty("mapName", getLocalizedName(locale));
        obj.addProperty("maxPeople", maxPeople);
        obj.addProperty("preview", previewResourceId);
        obj.addProperty("maxRank", maxRank);
        obj.addProperty("minRank", minRank);
        obj.addProperty("theme", theme.getName());
        obj.add("supportedModes", supportedModesList);

        return obj;
    }

    public String getLocalizedName(Locale locale) {
        return MapNames.getName(mapId, locale);
    }
}
