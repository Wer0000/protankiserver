package ua.lann.protankiserver.orm.entities.garage;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import ua.lann.protankiserver.enums.GarageItemType;
import ua.lann.protankiserver.enums.Rank;
import ua.lann.protankiserver.game.localization.GarageItemLocalizedData;
import ua.lann.protankiserver.game.localization.GarageItemsLocalization;
import ua.lann.protankiserver.models.garage.GarageItemProperty;

import java.util.ArrayList;
import java.util.List;

@Getter @Setter
@MappedSuperclass
public class BaseGarageItem {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private long identityId;

    @Column private String id;

    @Column private int itemIndex;
    @Column @Enumerated(EnumType.STRING) private GarageItemType type;
    @Column private String category;

    @Column @Enumerated(EnumType.STRING) private Rank rank;
    @Column @Enumerated(EnumType.STRING) private Rank nextRank;

    @Column private int price;
    @Column private int nextPrice;
    @ManyToOne @JoinColumn(name = "discount_id") private DiscountEntity discount;

    @Column private int baseItemId;
    @Column private int previewResourceId;

    @Column private long remainingTimeInSeconds;

    private transient List<GarageItemProperty> properties = new ArrayList<>();

    public GarageItemLocalizedData getLocalizedData() {
        return GarageItemsLocalization.getData(id);
    }
}
