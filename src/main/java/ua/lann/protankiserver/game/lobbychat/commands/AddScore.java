package ua.lann.protankiserver.game.lobbychat.commands;

import ua.lann.protankiserver.ClientController;
import ua.lann.protankiserver.enums.PlayerPermissions;
import ua.lann.protankiserver.game.lobbychat.LobbyChat;

public class AddScore extends CommandBase {
    public AddScore(LobbyChat chat) {
        super(chat, PlayerPermissions.AddExperience);
    }

    @Override
    public void execute(ClientController controller, String[] args) {
        int amount = Integer.parseInt(args[0]);
        controller.addExperience(amount);

        chat.sendMessage(controller, chat.constructSystemMessage(
            "Added " + amount + " experience to player " + controller.getPlayer().getNickname()
        ));
    }
}
