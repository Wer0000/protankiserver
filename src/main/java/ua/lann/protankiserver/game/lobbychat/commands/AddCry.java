package ua.lann.protankiserver.game.lobbychat.commands;

import ua.lann.protankiserver.ClientController;
import ua.lann.protankiserver.enums.PlayerPermissions;
import ua.lann.protankiserver.game.lobbychat.LobbyChat;

public class AddCry extends CommandBase {
    public AddCry(LobbyChat chat) { super(chat, PlayerPermissions.AddCrystals); }

    @Override
    public void execute(ClientController controller, String[] args) {
        int amount = Integer.parseInt(args[0]);
        controller.addCrystals(amount);

        chat.sendMessage(controller, chat.constructSystemMessage(
            "Added " + amount + " crystals to player " + controller.getPlayer().getNickname()
        ));
    }
}
