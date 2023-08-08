package ua.lann.protankiserver.orm.entities.garage.items;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import ua.lann.protankiserver.orm.entities.garage.BaseGarageItem;
import ua.lann.protankiserver.orm.entities.garage.ResistancesEntity;

@Getter
@Setter
@Entity
public class PaintGarageItem extends BaseGarageItem {
    @Column private int coloring;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "resistances_id")
    private ResistancesEntity resistances;
}
