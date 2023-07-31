package ua.lann.protankiserver.protocol.packets.handlers.lobby;

import io.netty.buffer.ByteBuf;
import ua.lann.protankiserver.ClientController;
import ua.lann.protankiserver.Server;
import ua.lann.protankiserver.lobbychat.ChatMessage;
import ua.lann.protankiserver.protocol.packets.CodecRegistry;
import ua.lann.protankiserver.protocol.packets.codec.ICodec;
import ua.lann.protankiserver.protocol.packets.handlers.IHandler;

public class LobbyChatSendMessage implements IHandler {
    @Override
    public void handle(ClientController channel, ByteBuf buf) {
        ICodec<String> stringICodec = CodecRegistry.getCodec(String.class);

        String targetName = stringICodec.decode(buf);
        String message = stringICodec.decode(buf);

        // Todo: add commands processing

        ChatMessage msg = new ChatMessage();

        msg.setSender(channel);
        msg.setTargeted(targetName != null);
        msg.setTarget(targetName);
        msg.setMessage(message);

        Server.getInstance().getLobbyChat().sendMessage(channel, msg);
    }
}
