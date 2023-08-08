package ua.lann.protankiserver.models.battle;

import com.squareup.moshi.Json;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import ua.lann.protankiserver.enums.BattleMode;
import ua.lann.protankiserver.enums.BattleSuspictionLevel;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class BattleListInfo {
    private String name;
    @Json(name = "battleId") private String id;
    @Json(name = "map") private String mapId;
    @Json(name = "preview") private int previewResourceId;

    @Json(name = "battleMode") private BattleMode mode;

    private int maxPeople;
    private int minRank;
    private int maxRank;

    private boolean equipmentConstraintsMode; // XP / BP / XPBP (See BattleFormat enum)
    @Json(name = "privateBattle") private boolean isPrivate;
    @Json(name = "proBattle") private boolean isProBattle;
    @Json(name = "parkourMode") private boolean isParkourMode;

    private List<String> users;
    private List<String> usersBlue;
    private List<String> usersRed;

    @Json(name = "suspicionLevel") private BattleSuspictionLevel suspictionLevel;
}
