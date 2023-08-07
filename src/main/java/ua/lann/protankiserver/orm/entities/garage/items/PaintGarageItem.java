package ua.lann.protankiserver.orm.entities.garage.items;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;
import ua.lann.protankiserver.orm.entities.garage.BaseGarageItem;

@Getter
@Setter
@Entity
public class PaintGarageItem extends BaseGarageItem {
    @Column private int coloring;
}
