package ua.lann.protankiserver;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.socket.SocketChannel;
import lombok.Getter;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.lann.protankiserver.enums.Achievement;
import ua.lann.protankiserver.enums.Layout;
import ua.lann.protankiserver.game.FriendsManager;
import ua.lann.protankiserver.models.ClientLayout;
import ua.lann.protankiserver.game.localization.Locale;
import ua.lann.protankiserver.models.profile.PlayerProfileManager;
import ua.lann.protankiserver.orm.HibernateUtils;
import ua.lann.protankiserver.orm.entities.Player;
import ua.lann.protankiserver.game.protocol.Encryption;
import ua.lann.protankiserver.game.protocol.packets.CodecRegistry;
import ua.lann.protankiserver.game.protocol.packets.PacketId;
import ua.lann.protankiserver.game.protocol.packets.codec.ICodec;
import ua.lann.protankiserver.game.resources.ResourcesManager;
import ua.lann.protankiserver.game.screens.ScreenManager;

public class ClientController {
    private final Logger logger = LoggerFactory.getLogger(ClientController.class);

    @Getter private final SocketChannel socket;
    @Getter private final Encryption encryption;
    @Getter private final ResourcesManager resourcesManager;

    @Getter private final ClientLayout layout;
    @Getter private Locale locale;
    @Getter private final FriendsManager friendsManager;

    @Getter private Player player;
    @Getter private PlayerProfileManager profileManager;

    @Getter private final ScreenManager screenManager;

    public ClientController(SocketChannel socket) {
        this.socket = socket;
        this.encryption = new Encryption();
        this.resourcesManager = new ResourcesManager(this);
        this.friendsManager = new FriendsManager(this);
        this.profileManager = new PlayerProfileManager(this);
        this.screenManager = new ScreenManager(this);
        layout = new ClientLayout();
        locale = Locale.Russian;
    }

    public void switchLayout() {
        ByteBuf buf = Unpooled.buffer();

        buf.writeInt(layout.back.getId());
        buf.writeInt(layout.front.getId());

        logger.info("Switch layout: {} -> {}", layout.back, layout.front);
        sendPacket(PacketId.SetLayout, buf);
        buf.release();
    }

    public void loadLayout(Layout layout, Layout back) {
        this.layout.front = layout;
        this.layout.back = back;

        ByteBuf buf = Unpooled.buffer();
        buf.writeInt(layout.getId());

        sendPacket(PacketId.ConfirmLayoutAccessible, buf);
        buf.release();

        switchLayout();
    }

    public void initAchievements() {
        Achievement[] achievements = { Achievement.FirstRankUp };
        ByteBuf buf = Unpooled.buffer();

        buf.writeInt(achievements.length);
        for(Achievement achievement : achievements) buf.writeInt(achievement.getId());

        sendPacket(PacketId.InitAchievements, buf);

        buf.release();
    }


    public void setPlayer(Player player) {
        this.player = player;
    }

    public void setLocale(Locale locale) {
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

    public void Dispose() {
        try(Session session = HibernateUtils.session()) {
            Server.getInstance().getLobbyChat().removeMember(this);
            Server.getInstance().removeActiveController(this);
            if(this.socket.isOpen()) this.socket.close().sync();

            session.beginTransaction();
            session.merge(player);
            session.getTransaction().commit();

            logger.info("Disposed successfully!");
        } catch (InterruptedException e) {
            logger.error("Failed to gracefully shut client socket:", e);
        }
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
