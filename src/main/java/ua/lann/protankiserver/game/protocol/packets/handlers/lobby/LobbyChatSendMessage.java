package ua.lann.protankiserver.game.protocol.packets.handlers.lobby;

import io.netty.buffer.ByteBuf;
import ua.lann.protankiserver.ClientController;
import ua.lann.protankiserver.Server;
import ua.lann.protankiserver.reflection.annotations.PacketHandler;
import ua.lann.protankiserver.game.lobbychat.ChatMessage;
import ua.lann.protankiserver.game.protocol.packets.CodecRegistry;
import ua.lann.protankiserver.game.protocol.packets.PacketId;
import ua.lann.protankiserver.game.protocol.packets.codec.ICodec;
import ua.lann.protankiserver.game.protocol.packets.handlers.IHandler;

@PacketHandler(packetId = PacketId.LobbyChatSendMessage)
public class LobbyChatSendMessage implements IHandler {
    @Override
    public void handle(ClientController channel, ByteBuf buf) {
        ICodec<String> stringICodec = CodecRegistry.getCodec(String.class);

        String targetName = stringICodec.decode(buf);
        String message = stringICodec.decode(buf);

        if(message.startsWith("/")) {
            Server.getInstance().getLobbyChat().processCommand(channel, message.substring(1));
            return;
        }

        ChatMessage msg = new ChatMessage();

        msg.setSender(channel);
        msg.setTargeted(targetName != null);
        msg.setTarget(targetName);
        msg.setMessage(message);

        Server.getInstance().getLobbyChat().sendMessage(channel, msg);
    }
}
