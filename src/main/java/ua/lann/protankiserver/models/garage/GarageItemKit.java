package ua.lann.protankiserver.models.garage;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

// TODO
@Getter @Setter
public class GarageItemKit {
    private int image;
    private int discountInPercent;
    private List<GarageItemKitItem> kitItems;
    private boolean isTimeless;
    private int timeLeftInSeconds;
}
