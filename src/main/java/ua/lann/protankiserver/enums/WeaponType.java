package ua.lann.protankiserver.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum WeaponType {
    Smoky("smoky");

    private final String id;

    public static WeaponType getById(String id) {
        for (WeaponType weaponType : WeaponType.values()) {
            if (weaponType.id.equals(id)) {
                return weaponType;
            }
        }

        return null;
    }
}