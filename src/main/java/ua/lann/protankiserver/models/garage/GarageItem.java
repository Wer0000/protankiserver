package ua.lann.protankiserver.models.garage;

import com.squareup.moshi.Json;
import lombok.Getter;
import lombok.Setter;
import ua.lann.protankiserver.enums.GarageItemType;
import ua.lann.protankiserver.enums.Rank;
import ua.lann.protankiserver.game.localization.GarageItemLocalizedData;
import ua.lann.protankiserver.orm.models.GarageItemProperty;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter @Setter
public class GarageItem implements Cloneable {
    @Json private String id;
    @Json private int index;
    @Json private GarageItemType type;
    @Json private String category;
    @Json(name = "isInventory") private boolean isCountable = false;

    @Json private boolean isForRent = false;
    @Json private boolean grouped = false;

    @Json private String name;
    @Json private String description;

    @Json private Rank rank;
    @Json(name = "next_rank") private Rank nextRank;

    private transient GarageItemLocalizedData localizedData;

    @Json private int price;
    @Json(name = "next_price") private int nextPrice;
    @Json private Discount discount;

    @Json private int baseItemId;
    @Json private int previewResourceId;

    @Json(name = "remainingTimeInSec") private long remainingTimeInSeconds;
    @Json private List<GarageItemProperty> properts;

    private transient List<GarageItemProperty> properties;

    // Weapon / Hull
    @Json private Integer modificationID = null;
    @Json private Integer object3ds = null;

    // Paint
    @Json private Integer coloring = null;
    private transient PaintResistances resistances = null;

    // Supply
    @Json private Integer count = null;

    // Kit
//    @Json private GarageItemKit kit;

    @Override
    public GarageItem clone() {
        try {
            return (GarageItem) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
    }
}
