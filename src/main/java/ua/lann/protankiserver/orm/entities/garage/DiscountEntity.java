package ua.lann.protankiserver.orm.entities.garage;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class DiscountEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private long id;

    @Column private int percent;
    @Column private int timeLeftInSeconds;
    @Column private int timeToStartInSeconds;
}
