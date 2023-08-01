package ua.lann.protankiserver.screens.lobby;

import ua.lann.protankiserver.ClientController;
import ua.lann.protankiserver.Server;
import ua.lann.protankiserver.battles.BattlesManager;
import ua.lann.protankiserver.enums.Layout;
import ua.lann.protankiserver.lobbychat.LobbyChat;
import ua.lann.protankiserver.battles.map.MapManager;
import ua.lann.protankiserver.screens.ScreenBase;

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
