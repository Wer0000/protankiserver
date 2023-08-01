package ua.lann.protankiserver.models;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Getter;
import ua.lann.protankiserver.enums.BattleMode;

@Getter
@AllArgsConstructor
public class BattleLimit {
    private BattleMode mode;
    private int scoreLimit;
    @SerializedName("timeLimitInSec") private int timeLimitInSeconds;
}
