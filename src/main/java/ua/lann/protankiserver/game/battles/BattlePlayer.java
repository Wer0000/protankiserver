package ua.lann.protankiserver.game.battles;

import lombok.Getter;
import ua.lann.protankiserver.ClientController;

public class BattlePlayer {
    @Getter private final ClientController controller;
    public BattlePlayer(ClientController controller) {
        this.controller = controller;
    }
}
