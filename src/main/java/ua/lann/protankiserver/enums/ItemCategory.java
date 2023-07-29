package ua.lann.protankiserver.enums;

public enum ItemCategory {
    Weapon(0),
    Armor(1),
    Color(2),
    Inventory(3),
    Plugin(4),
    Kit(5),
    Emblem(6),
    Present(7),
    GivenPresent(8);

    public final int id;
    ItemCategory(int id) {
        this.id = id;
    }
}
