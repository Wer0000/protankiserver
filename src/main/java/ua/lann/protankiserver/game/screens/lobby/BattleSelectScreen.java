package ua.lann.protankiserver.game.screens.lobby;

import ua.lann.protankiserver.ClientController;
import ua.lann.protankiserver.Server;
import ua.lann.protankiserver.game.battles.BattlesManager;
import ua.lann.protankiserver.enums.Layout;
import ua.lann.protankiserver.game.lobbychat.LobbyChat;
import ua.lann.protankiserver.game.battles.map.MapManager;
import ua.lann.protankiserver.game.screens.ScreenBase;

public class BattleSelectScreen extends ScreenBase {
    public BattleSelectScreen(ClientController controller) {
        super(controller);
    }

    @Override
    public void open() {
        controller.loadLayout(Layout.Lobby, Layout.Lobby);

        MapManager.sendMapList(controller);
        BattlesManager.sendBattlesList(controller);

        LobbyChat chat = Server.getInstance().getLobbyChat();
        chat.addMember(controller);

        chat.configure(controller);
        chat.setupChatDelay(controller);
        chat.addMessages(controller, null, true);
    }

    @Override
    public void close() {

    }
}
