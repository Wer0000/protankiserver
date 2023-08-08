package ua.lann.protankiserver.orm.entities.garage;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import ua.lann.protankiserver.models.garage.PaintResistances;

@Getter @Setter
@Entity
public class ResistancesEntity extends PaintResistances {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;
}
