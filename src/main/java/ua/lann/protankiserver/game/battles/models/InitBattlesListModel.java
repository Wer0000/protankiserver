package ua.lann.protankiserver.game.battles.models;

import com.squareup.moshi.Json;
import lombok.Getter;
import lombok.Setter;
import ua.lann.protankiserver.models.battle.BattleListInfo;

import java.util.List;

@Getter @Setter
public class InitBattlesListModel {
    @Json List<BattleListInfo> battles;
}
