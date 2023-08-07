package ua.lann.protankiserver.models.garage;

import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import lombok.Setter;
import ua.lann.protankiserver.enums.GarageItemType;
import ua.lann.protankiserver.enums.Rank;
import ua.lann.protankiserver.game.localization.GarageItemLocalizedData;

import java.util.List;

@Getter @Setter
public class GarageItem implements Cloneable {
    private String id;
    private int index;
    private GarageItemType type;
    private String category;
    @SerializedName("isInventory") private boolean isCountable = false;

    private boolean isForRent = false;
    private boolean grouped = false;

    private String name;
    private String description;

    private Rank rank;
    @SerializedName("next_rank") private Rank nextRank;

    private transient GarageItemLocalizedData localizedData;

    private int price;
    @SerializedName("next_price") private int nextPrice;
    private Discount discount;

    private int baseItemId;
    private int previewResourceId;

    @SerializedName("remainingTimeInSec") private long remainingTimeInSeconds;

    @SerializedName("properts") private List<GarageItemProperty> properties;

    // Weapon / Hull
    private Integer modificationID = null;
    private Integer object3ds = null;

    // Paint
    private Integer coloring = null;

    // Supply
    private Integer count = null;

    // Kit
    private GarageItemKit kit;

    @Override
    public GarageItem clone() {
        try {
            return (GarageItem) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
    }
}
