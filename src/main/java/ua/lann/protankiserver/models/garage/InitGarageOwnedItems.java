package ua.lann.protankiserver.models.garage;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
public class InitGarageOwnedItems {
    private List<GarageItem> items;
    private final int garageBoxId = 170001;
}
