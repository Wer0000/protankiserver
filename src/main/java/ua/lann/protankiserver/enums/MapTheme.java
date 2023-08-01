package ua.lann.protankiserver.enums;

import lombok.Getter;

@Getter
public enum MapTheme {
    SUMMER("SUMMER"),
    WINTER("WINTER"),
    SPACE("SPACE"),
    SUMMER_DAY("SUMMER_DAY"),
    SUMMER_NIGHT("SUMMER_NIGHT"),
    WINTER_DAY("WINTER_DAY");

    private final String name;

    MapTheme(String name) {
        this.name = name;
    }

    public static MapTheme fromString(String name) {
        for (MapTheme theme : MapTheme.values()) {
            if (theme.name.equals(name)) {
                return theme;
            }
        }
        return null;
    }

    public int getId() {
        return ordinal();
    }
}
