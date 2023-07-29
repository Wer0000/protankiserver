package ua.lann.protankiserver.enums;

public enum DamageIndicatorType {
    Normal,
    Critical,
    Fatal,
    Heal;

    public int getId() { return ordinal(); }
}
