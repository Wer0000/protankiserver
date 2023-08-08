package ua.lann.protankiserver.orm.entities.garage;

import jakarta.persistence.*;
import lombok.*;

@RequiredArgsConstructor
@NoArgsConstructor
@Getter @Setter
@Entity
public class EquippedTankData {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private long id;

    @NonNull @Column private String weapon;
    @NonNull @Column private String hull;
    @NonNull @Column private String paint;
}
