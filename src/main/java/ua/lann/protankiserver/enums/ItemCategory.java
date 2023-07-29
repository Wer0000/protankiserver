package ua.lann.protankiserver.enums;

public enum ItemCategory {
    Weapon,
    Armor,
    Color,
    Inventory,
    Plugin,
    Kit,
    Emblem,
    Present,
    GivenPresent;

    public int getId() { return ordinal(); }
}
