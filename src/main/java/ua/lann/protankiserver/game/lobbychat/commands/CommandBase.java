package ua.lann.protankiserver.game.lobbychat.commands;

import lombok.Getter;
import ua.lann.protankiserver.ClientController;
import ua.lann.protankiserver.enums.PlayerPermissions;
import ua.lann.protankiserver.game.lobbychat.LobbyChat;

public abstract class CommandBase {
    @Getter protected final PlayerPermissions requiredPerms;
    @Getter protected final LobbyChat chat;

    public CommandBase(LobbyChat chat, PlayerPermissions requiredPerms) {
        this.chat = chat;
        this.requiredPerms = requiredPerms;
    }

    public abstract void execute(ClientController controller, String[] args);
}
