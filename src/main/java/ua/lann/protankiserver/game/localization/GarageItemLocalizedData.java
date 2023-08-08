package ua.lann.protankiserver.game.localization;

import java.util.HashMap;

public class GarageItemLocalizedData {
    private final HashMap<Locale, String> nameMap = new HashMap<>();
    private final HashMap<Locale, String> descriptionMap = new HashMap<>();

    public void setName(Locale locale, String name) {
        nameMap.put(locale, name);
    }
    public String getName(Locale locale) {
        return nameMap.get(locale);
    }

    public void setDescription(Locale locale, String desc) {
        descriptionMap.put(locale, desc);
    }
    public String getDescription(Locale locale) {
        return descriptionMap.get(locale);
    }
}
