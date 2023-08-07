package ua.lann.protankiserver.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum GarageItemType {
    Weapon(1, "weapon"),
    Hull(2, "armor"),
    Paint(3, "paint"),
    Supply(4, "inventory"),
    Subscription(5, "special"),
    Kit(6, "kit"),
    Present(7, "special");

    private final int key;
    private final String categoryKey;
}
