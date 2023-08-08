package ua.lann.protankiserver.game.battles.models;

import com.squareup.moshi.Json;
import lombok.Getter;
import lombok.Setter;
import ua.lann.protankiserver.models.battle.BattleLimit;

import java.util.List;

@Getter
@Setter
public class InitMapListModel {
    @Json int maxRangeLength;
    @Json boolean battleCreationDisabled;
    @Json List<BattleLimit> battleLimits;
    @Json List<Map> maps;
}
