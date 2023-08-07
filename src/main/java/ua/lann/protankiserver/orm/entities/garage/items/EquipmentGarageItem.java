package ua.lann.protankiserver.orm.entities.garage.items;

import com.google.gson.JsonObject;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.PostLoad;
import lombok.Getter;
import lombok.Setter;
import ua.lann.protankiserver.enums.GarageItemType;
import ua.lann.protankiserver.game.garage.GarageItemPropertyConverter;
import ua.lann.protankiserver.orm.entities.garage.BaseGarageItem;
import ua.lann.protankiserver.util.JsonUtils;

@Getter
@Setter
@Entity
public class EquipmentGarageItem extends BaseGarageItem {
    @Column private int modification;
    @Column private int object3ds;

    @PostLoad
    private void afterLoad() {
        String type = getType().equals(GarageItemType.Weapon) ? "weapons" : "hulls";
        JsonObject obj = JsonUtils.readJsonObject("data/" + type + ".json")
            .getAsJsonObject(getId())
            .getAsJsonObject(String.valueOf(modification))
            .getAsJsonObject("propers");
        this.setProperties(GarageItemPropertyConverter.convertJsonObject(obj));
    }
}
