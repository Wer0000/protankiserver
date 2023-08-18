package ua.lann.protankiserver.orm.entities.garage.items;

import com.squareup.moshi.Types;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.PostLoad;
import lombok.Getter;
import lombok.Setter;
import ua.lann.protankiserver.enums.GarageItemType;
import ua.lann.protankiserver.game.garage.GarageItemPropertyConverter;
import ua.lann.protankiserver.orm.entities.garage.BaseGarageItem;
import ua.lann.protankiserver.orm.models.GarageItemRawData;
import ua.lann.protankiserver.orm.models.GarageItemRawProperty;
import ua.lann.protankiserver.serialization.JsonUtils;

import java.util.Map;

@Getter
@Setter
@Entity
public class EquipmentGarageItem extends BaseGarageItem {
    @Column private int modification;
    @Column private int object3ds;

    @PostLoad
    private void afterLoad() {
        String type = getType().equals(GarageItemType.Weapon) ? "weapons" : "hulls";
        Map<String, Map<String, GarageItemRawData>> model = JsonUtils.readResource("data/" + type + ".json", Types.newParameterizedType(
            Map.class,
            String.class,
            Types.newParameterizedType(
                Map.class,
                String.class,
                GarageItemRawData.class
            )
        ));

        Map<String, GarageItemRawProperty> props = model
            .get(getId())
            .get(String.valueOf(modification))
            .getProperties();

        this.setProperties(GarageItemPropertyConverter.convert(props));
    }
}
