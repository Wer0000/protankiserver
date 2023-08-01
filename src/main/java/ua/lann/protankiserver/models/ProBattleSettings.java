package ua.lann.protankiserver.models;

import lombok.*;
import ua.lann.protankiserver.enums.BattleFormat;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProBattleSettings {
    private boolean rearmorring = false;
    private boolean crystalDropDisabled = false;
    private boolean supplyDropDisabled = false;
    private boolean suppliesDisabled = false;
    private boolean isPrivate = false;

    private BattleFormat format = BattleFormat.None;
}
