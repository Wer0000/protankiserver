package ua.lann.protankiserver.models.garage.data;

import lombok.Getter;
import lombok.Setter;
import ua.lann.protankiserver.models.garage.GarageItemProperty;

import java.util.List;

@Getter @Setter
public class ItemDataModel {
    private int modification;
    
    private List<GarageItemProperty> properties;
    private int object3ds;
    private int baseItemId;
    private int previewResourceId;
//    private ItemSFXData sfxData;
}
