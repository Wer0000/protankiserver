package ua.lann.protankiserver.screens;

import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.lann.protankiserver.ClientController;
import ua.lann.protankiserver.screens.auth.AuthorizationScreen;
import ua.lann.protankiserver.screens.lobby.BattleSelectScreen;

import java.util.HashMap;

public class ScreenManager {
    private static final Logger logger = LoggerFactory.getLogger(ScreenManager.class);

    public final ClientController controller;

    private final HashMap<Class<? extends ScreenBase>, ScreenBase> screens;
    @Getter private ScreenBase screen;

    public ScreenManager(ClientController controller) {
        this.controller = controller;

        screens = new HashMap<>();
        screens.put(AuthorizationScreen.class, new AuthorizationScreen(controller));
        screens.put(BattleSelectScreen.class, new BattleSelectScreen(controller));
    }

    public ScreenBase getScreenInstance(Class<? extends ScreenBase> screen) {
        return screens.get(screen);
    }

    public void setScreen(Class<? extends ScreenBase> screen) {
        logger.info("Switch screen to: {}", screen.getName());
        this.screen = getScreenInstance(screen);
        this.screen.open();
    }
}
