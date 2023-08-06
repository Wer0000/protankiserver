package ua.lann.protankiserver.game.lobbychat;

import lombok.Getter;
import lombok.Setter;
import ua.lann.protankiserver.ClientController;

@Getter
@Setter
public class ChatMessage {
    private boolean isTargeted;
    private ClientController sender;
    private String target;
    private String message;

    public ChatMessageType getType() {
        if(isTargeted) return ChatMessageType.Targeted;
        if(sender == null) return ChatMessageType.System;
        if(target == null) return ChatMessageType.Normal;
        return ChatMessageType.Private;
    }
}
