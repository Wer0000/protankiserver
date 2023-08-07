package ua.lann.protankiserver.models.garage;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class Discount {
    private int percent;
    private int timeLeftInSeconds;
    private int timeToStartInSeconds;
}
