package ua.lann.protankiserver.models.battle;

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
public class BattleListInfo {
    private String name;
    @SerializedName("battleId") private String id;
    @SerializedName("map") private String mapId;
    @SerializedName("preview") private int previewResourceId;

    @SerializedName("battleMode") private BattleMode mode;

    private int maxPeople;
    private int minRank;
    private int maxRank;

    private boolean equipmentConstraintsMode; // XP / BP / XPBP (See BattleFormat enum)
    @SerializedName("privateBattle") private boolean isPrivate;
    @SerializedName("proBattle") private boolean isProBattle;
    @SerializedName("parkourMode") private boolean isParkourMode;

    private List<String> users;
    private List<String> usersBlue;
    private List<String> usersRed;

    @SerializedName("suspicionLevel") private BattleSuspictionLevel suspictionLevel;
}
