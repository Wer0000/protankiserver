package ua.lann.protankiserver.models;

import com.google.gson.annotations.SerializedName;
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
    @SerializedName("itemId") private String id;
    @SerializedName("map") private String mapId;
    @SerializedName("preview") private int previewResourceId;

    @SerializedName("battleMode") private BattleMode mode;

    @SerializedName("maxPeopleCount") private int maxPeople;
    private int scoreLimit;
    private int minRank;
    private int maxRank;

    @SerializedName("timeLimitInSec") private int timeLimitInSeconds;
    @SerializedName("proBattleTimeLimitInSec") private int proBattleTimeLimitInSeconds;

    private boolean equipmentConstraintsMode; // XP / BP / XPBP (See BattleFormat enum)
    @SerializedName("privateBattle") private boolean isPrivate;
    @SerializedName("proBattle") private boolean isProBattle;
    @SerializedName("parkourMode") private boolean isParkourMode;

    @SerializedName("suspicionLevel") private BattleSuspictionLevel suspictionLevel;

    @SerializedName("reArmorEnabled") private boolean isRearmoringEnabled;
    @SerializedName("roundStarted") private boolean isRoundStarted;
    @SerializedName("withoutBonuses") private boolean isWithoutBonuses;
    @SerializedName("withoutSupplies") private boolean isWithoutSupplies;
    @SerializedName("withoutCrystals") private boolean isWithoutCrystals;

    // For DM battle
    private List<String> users;

    // For Others
    private List<String> usersBlue;
    private List<String> usersRed;

    private int scoreBlue;
    private int scoreRed;

    @SerializedName("friendlyFire") private boolean isFriendlyFireEnabled;
    @SerializedName("autoBalance") private boolean isAutobalanceEnabled;

    @SerializedName("userPaidNoSuppliesBattle") private boolean hasProBattlePass;
    @SerializedName("spectator") private boolean isSpectator;

    private int proBattleEnterPrice;
}
