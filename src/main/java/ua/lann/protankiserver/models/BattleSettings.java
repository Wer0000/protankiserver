package ua.lann.protankiserver.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import ua.lann.protankiserver.enums.BattleMode;
import ua.lann.protankiserver.enums.Rank;

@Getter
@Setter
@AllArgsConstructor
public class BattleSettings {
    private final BattleMode mode;
    private final int maxPeople;
    private final Rank maxRank;
    private final Rank minRank;

    private int scoreLimit;
    private int timeLimitInSeconds;
}
