package ua.lann.protankiserver.orm.entities.garage;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Embeddable
@Getter @Setter
public class OwnedGarageItem {
    private String id;
    private int modification;
}
