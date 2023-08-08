package ua.lann.protankiserver.models.battle;

import com.squareup.moshi.Json;
import lombok.AllArgsConstructor;
import lombok.Getter;
import ua.lann.protankiserver.enums.BattleMode;

@Getter
@AllArgsConstructor
public class BattleLimit {
    private BattleMode mode;
    private int scoreLimit;
    @Json(name = "timeLimitInSec") private int timeLimitInSeconds;
}
