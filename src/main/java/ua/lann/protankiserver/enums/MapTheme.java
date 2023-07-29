package ua.lann.protankiserver.enums;

public enum MapTheme {
    Summer,
    Winter,
    Space,
    SummerDay,
    SummerNight,
    WinterDay;

    public int getId() {
        return ordinal();
    }
}
