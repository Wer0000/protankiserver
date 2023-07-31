package ua.lann.protankiserver;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.socket.SocketChannel;
import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.lann.protankiserver.enums.Achievement;
import ua.lann.protankiserver.enums.ChatModeratorLevel;
import ua.lann.protankiserver.enums.Layout;
import ua.lann.protankiserver.lobbychat.LobbyChat;
import ua.lann.protankiserver.orm.entities.Player;
import ua.lann.protankiserver.protocol.Encryption;
import ua.lann.protankiserver.protocol.packets.CodecRegistry;
import ua.lann.protankiserver.protocol.packets.PacketId;
import ua.lann.protankiserver.protocol.packets.codec.ICodec;
import ua.lann.protankiserver.resources.ResourcesManager;
import ua.lann.protankiserver.screens.ScreenBase;
import ua.lann.protankiserver.screens.auth.AuthorizationScreen;

import java.util.HashMap;

public class ClientController {
    private final Logger logger = LoggerFactory.getLogger(ClientController.class);

    private final SocketChannel socket;
    protected final Encryption encryption;
    public final ResourcesManager resources;

    @Getter private final ClientLayout layout;
    @Getter private String locale;
    @Getter private PlayerProfile profile;

    private final HashMap<Class<? extends ScreenBase>, ScreenBase> screens;
    @Getter private ScreenBase screen;

    public ClientController(SocketChannel socket) {
        this.socket = socket;
        this.encryption = new Encryption();
        this.resources = new ResourcesManager(this);

        layout = new ClientLayout();

        locale = "ru";
        screens = new HashMap<>();
        screens.put(AuthorizationScreen.class, new AuthorizationScreen(this));
    }

    public void switchLayout() {
        ByteBuf buf = Unpooled.buffer();

        buf.writeInt(layout.back.getId()); // back
        buf.writeInt(layout.front.getId()); // front

        switch (layout.front) {
            case Lobby -> {
                // TODO: chat, lobby, send battle infos
                break;
            }

            case Garage -> {
                // TODO: chat, garage
                break;
            }

            default -> { break; }
        }

        sendPacket(PacketId.SetLayout, buf);
        buf.release();
    }

    public void loadLayout(Layout layout, Layout back, boolean showNews) {
        logger.info("Switch layout: {}, showNews: {}", layout, showNews);

        this.layout.front = layout;
        this.layout.back = back;

        ByteBuf buf = Unpooled.buffer();

        buf.writeInt(layout.getId());
        sendPacket(PacketId.ConfirmLayoutAccessible, buf);

        buf.release();

        if(showNews) {
            // Todo: show news
        }

        switch (layout) {
            case Lobby -> {
                // TODO: Load maps list
                // TODO: Load battle list
                LobbyChat chat = Server.getInstance().getLobbyChat();
                chat.addMember(this);

                chat.configure(this);
                chat.setupChatDelay(this);
                chat.loadMessagesHistory(this, null, true);

                switchLayout();
                break;
            }

            default -> {
                logger.warn("Unsupported layout: {}", layout);
            }
        }
    }

    public void initAchievements() {
        Achievement[] achievements = { Achievement.FirstRankUp };
        ByteBuf buf = Unpooled.buffer();

        buf.writeInt(achievements.length);
        for(Achievement achievement : achievements) buf.writeInt(achievement.getId());

        sendPacket(PacketId.InitAchievements, buf);

        buf.release();
    }

    public ScreenBase getScreenInstance(Class<? extends ScreenBase> screen) {
        return screens.get(screen);
    }

    public void setPlayer(Player player) {
        this.profile = new PlayerProfile(player, this);
    }

    public void setScreen(Class<? extends ScreenBase> screen) {
        this.screen = getScreenInstance(screen);
        this.screen.open();
    }

    public void setLocale(String locale) {
        if(!locale.equals("ru")) {
            logger.warn("Unsupported locale: {}", locale);
            return;
        }

        this.locale = locale;
        logger.info("Locale set to: {}", locale);
    }

    public void sendPacketUnencrypted(PacketId packetId, ByteBuf data) {
        ByteBuf packet = Unpooled.buffer()
            .writeInt(data.readableBytes() + 8)
            .writeInt(packetId.packetId)
            .writeBytes(data);

        logger.info("Sent Unencrypted [{}]: {}", packetId, toHexString(packet));
        socket.writeAndFlush(packet);
    }

    public void sendPacket(PacketId packetId, ByteBuf data) {
        ByteBuf _packet = encryption.encrypt(data);
        ByteBuf packet = Unpooled.buffer()
            // Lann: Dev is degenerative by doing + 8 for both writeInt
            .writeInt(_packet.readableBytes() + 8)
            .writeInt(packetId.packetId)
            .writeBytes(_packet);

        logger.info("Sent [{}]: {} bytes", packetId, packet.readableBytes());
        socket.writeAndFlush(packet);
    }

    public void alert(String message) {
        ByteBuf buf = Unpooled.buffer();

        ICodec<String> stringICodec = CodecRegistry.getCodec(String.class);
        stringICodec.encode(buf, message);

        this.sendPacket(PacketId.MessageAlert, buf);
        buf.release();
    }

    public static String toHexString(ByteBuf byteBuf) {
        StringBuilder sb = new StringBuilder();

        int idx = byteBuf.readerIndex();
        for (int i = idx; i < byteBuf.readableBytes(); i++) {
            byte b = byteBuf.getByte(i);
            sb.append(String.format("%02X", b));
            if (i < byteBuf.readableBytes() - 1) {
                sb.append(" ");
            }
        }

        byteBuf.readerIndex(idx);
        return sb.toString();
    }
}
