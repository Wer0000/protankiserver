package ua.lann.protankiserver.lobbychat;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import ua.lann.protankiserver.ClientController;
import ua.lann.protankiserver.models.profile.PlayerProfileManager;
import ua.lann.protankiserver.Server;
import ua.lann.protankiserver.enums.ChatModeratorLevel;
import ua.lann.protankiserver.orm.entities.Player;
import ua.lann.protankiserver.protocol.packets.CodecRegistry;
import ua.lann.protankiserver.protocol.packets.PacketId;
import ua.lann.protankiserver.protocol.packets.codec.ICodec;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class LobbyChat {
    private final Server server;
    private final List<ChatMessage> messages;
    private final List<ClientController> members;

    public LobbyChat(Server server) {
        this.server = server;
        this.messages = new ArrayList<>();
        this.members = new ArrayList<>();
    }

    public void removeMember(ClientController controller) { members.remove(controller); }
    public void addMember(ClientController controller) { members.add(controller); }

    public void broadcastPacket(PacketId packetId, ByteBuf buf) {
        this.members.forEach(x -> x.sendPacket(packetId, buf));
    }

    public void configure(ClientController controller) {
        ByteBuf buf = Unpooled.buffer();

        ICodec<String> stringICodec = CodecRegistry.getCodec(String.class);
        ICodec<Boolean> booleanICodec = CodecRegistry.getCodec(Boolean.class);

        booleanICodec.encode(buf, controller.getPlayer().getChatModeratorLevel().equals(ChatModeratorLevel.Administrator));
        booleanICodec.encode(buf, LobbyChatConfiguration.Antiflood.Enabled);
        buf.writeInt(60);
        booleanICodec.encode(buf, LobbyChatConfiguration.Enabled);
        buf.writeInt(controller.getPlayer().getChatModeratorLevel().getId());

        buf.writeByte(1); // dont ask

        buf.writeInt(3); // min chars
        buf.writeInt(1); // min words

        stringICodec.encode(buf, controller.getPlayer().getNickname());

        booleanICodec.encode(buf, LobbyChatConfiguration.ShowLinks);
        booleanICodec.encode(buf, LobbyChatConfiguration.Antiflood.TypingSpeedAntifloodEnabled);

        controller.sendPacket(PacketId.ConfigureLobbyChat, buf);
    }

    public void setupChatDelay(ClientController controller) {
        ByteBuf buf = Unpooled.buffer();

        buf.writeInt(LobbyChatConfiguration.SymbolCost);
        buf.writeInt(LobbyChatConfiguration.EnterCost);

        controller.sendPacket(PacketId.SetLobbyChatDelay, buf);
    }

    // Lann: if messages == null, loading messages history
    public void addMessages(ClientController controller, List<ChatMessage> messages, boolean isLocal) {
        ByteBuf buf = Unpooled.buffer();

        if(messages == null) messages = this.messages;

        ICodec<String> stringICodec = CodecRegistry.getCodec(String.class);
        ICodec<Boolean> booleanICodec = CodecRegistry.getCodec(Boolean.class);

        buf.writeInt(messages.size());

        for(ChatMessage message : messages) {
            Player sender = message.getSender().getPlayer();
            String target = message.getTarget();

            buf.writeByte(0);
            buf.writeInt(sender.getChatModeratorLevel().getId());
            stringICodec.encode(buf, "127.0.0.1");
            buf.writeInt(sender.getRank().getNumber());
            stringICodec.encode(buf, sender.getNickname());

            booleanICodec.encode(buf, message.getType().equals(ChatMessageType.System));

            Player targetPlayer = server.getPlayer(target);
            if(targetPlayer == null) buf.writeByte(1);
            else {
                buf.writeByte(0);
                buf.writeInt(targetPlayer.getChatModeratorLevel().getId());
                stringICodec.encode(buf, "127.0.0.1");
                buf.writeInt(targetPlayer.getRank().getNumber());
                stringICodec.encode(buf, targetPlayer.getNickname());
            }

            stringICodec.encode(buf, message.getMessage());
            booleanICodec.encode(buf, false); // TODO: Is message warning (Yellow)
        }

        if(isLocal) {
            controller.sendPacket(PacketId.LoadMessagesHistory, buf);
        } else {
            broadcastPacket(PacketId.LoadMessagesHistory, buf);
        }

        buf.release();
    }

    public void sendMessage(ClientController sender, ChatMessage msg) {
        this.messages.add(msg);
        addMessages(sender, Collections.singletonList(msg), false);
    }
}
