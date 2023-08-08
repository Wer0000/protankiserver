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
public class BattleDisplayInfo {
    private String name;
    @Json(name = "itemId") private String id;
    @Json(name = "map") private String mapId;
    @Json(name = "preview") private int previewResourceId;

    @Json(name = "battleMode") private BattleMode mode;

    @Json(name = "maxPeopleCount") private int maxPeople;
    private int scoreLimit;
    private int minRank;
    private int maxRank;

    @Json(name = "timeLimitInSec") private int timeLimitInSeconds;
    @Json(name = "proBattleTimeLimitInSec") private int proBattleTimeLimitInSeconds;

    private boolean equipmentConstraintsMode; // XP / BP / XPBP (See BattleFormat enum)
    @Json(name = "privateBattle") private boolean isPrivate;
    @Json(name = "proBattle") private boolean isProBattle;
    @Json(name = "parkourMode") private boolean isParkourMode;

    @Json(name = "suspicionLevel") private BattleSuspictionLevel suspictionLevel;

    @Json(name = "reArmorEnabled") private boolean isRearmoringEnabled;
    @Json(name = "roundStarted") private boolean isRoundStarted;
    @Json(name = "withoutBonuses") private boolean isWithoutBonuses;
    @Json(name = "withoutSupplies") private boolean isWithoutSupplies;
    @Json(name = "withoutCrystals") private boolean isWithoutCrystals;

    // For DM battle
    private List<String> users;

    // For Others
    private List<String> usersBlue;
    private List<String> usersRed;

    private int scoreBlue;
    private int scoreRed;

    @Json(name = "friendlyFire") private boolean isFriendlyFireEnabled;
    @Json(name = "autoBalance") private boolean isAutobalanceEnabled;

    @Json(name = "userPaidNoSuppliesBattle") private boolean hasProBattlePass;
    @Json(name = "spectator") private boolean isSpectator;

    private int proBattleEnterPrice;
}
