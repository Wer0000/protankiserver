package ua.lann.protankiserver.game.battles;

import lombok.Getter;
import ua.lann.protankiserver.ClientController;

public record BattlePlayer(@Getter ClientController controller) {
}
