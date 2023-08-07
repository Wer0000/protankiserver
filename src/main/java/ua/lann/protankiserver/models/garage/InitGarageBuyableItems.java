package ua.lann.protankiserver.models.garage;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
public class InitGarageBuyableItems {
    private List<GarageItem> items;
    private int delayMountArmorInSec = 0;
    private int delayMountWeaponInSec = 0;
    private int delayMountColorInSec = 0;
}
