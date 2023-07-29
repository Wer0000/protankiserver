package ua.lann.protankiserver.enums;

public enum MapTheme {
    Summer(0),
    Winter(1),
    Space(2),
    SummerDay(3),
    SummerNight(4),
    WinterDay(5);

    public final int id;
    MapTheme(int id) { this.id = id; }
}
