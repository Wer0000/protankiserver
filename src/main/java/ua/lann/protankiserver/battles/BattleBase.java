package ua.lann.protankiserver.battles;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import ua.lann.protankiserver.battles.map.Map;
import ua.lann.protankiserver.enums.BattleSuspictionLevel;
import ua.lann.protankiserver.models.BattleDisplayInfo;
import ua.lann.protankiserver.models.BattleListInfo;
import ua.lann.protankiserver.models.BattleSettings;
import ua.lann.protankiserver.models.ProBattleSettings;

@Getter
@AllArgsConstructor
public abstract class BattleBase {
    protected final String id;
    protected final String name;
    protected final Map map;
    protected final BattleSettings settings;
    protected BattleSuspictionLevel suspictionLevel;

    @NonNull protected boolean isRoundStarted;

    protected final boolean isProBattle;
    protected final ProBattleSettings proBattleSettings;

    public abstract BattleListInfo getBattleListInfo();
    public abstract BattleDisplayInfo getBattleDisplayInfo();
}
