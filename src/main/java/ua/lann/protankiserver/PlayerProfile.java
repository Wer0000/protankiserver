package ua.lann.protankiserver;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import lombok.Getter;
import lombok.Setter;
import ua.lann.protankiserver.enums.ChatModeratorLevel;
import ua.lann.protankiserver.enums.Rank;
import ua.lann.protankiserver.orm.entities.Player;
import ua.lann.protankiserver.protocol.packets.CodecRegistry;
import ua.lann.protankiserver.protocol.packets.PacketId;
import ua.lann.protankiserver.protocol.packets.codec.ICodec;

public class PlayerProfile {
    @Getter private final ClientController controller;

    @Getter @Setter private String nickname;

    @Getter @Setter private Rank rank;
    @Getter @Setter private int experience;
    @Getter @Setter private int crystals;

    @Getter @Setter private ChatModeratorLevel chatModeratorLevel;


    public PlayerProfile(Player player, ClientController controller) {
        this.controller = controller;

        this.nickname = player.getNickname();

        this.rank = player.getRank();
        this.experience = player.getExperience();
        this.crystals = player.getCrystals();
        this.chatModeratorLevel = player.getLevel();
    }

    public void sendPremiumInfo() {
        ByteBuf buf = Unpooled.buffer();

        ICodec<Boolean> booleanICodec = CodecRegistry.getCodec(Boolean.class);

        booleanICodec.encode(buf, false); // needShowNotificationCompletionPremium
        booleanICodec.encode(buf, false); // needShowWelcomeAlert
        buf.writeFloat(99665.5234375f); // reminderCompletionPremiumTime
        booleanICodec.encode(buf, true); // wasShowAlertForFirstPurchasePremium
        booleanICodec.encode(buf, false); // wasShowReminderCompletionPremium
        buf.writeInt(16777243); // Lifetime in seconds

        controller.sendPacket(PacketId.SetPremiumInfo, buf);
        buf.release();
    }

    public void sendProfileInfo() {
        ByteBuf buf = Unpooled.buffer();

        ICodec<Boolean> booleanICodec = CodecRegistry.getCodec(Boolean.class);
        ICodec<String> stringICodec = CodecRegistry.getCodec(String.class);

        buf.writeInt(crystals);
        buf.writeInt(rank.minExperience);
        buf.writeInt(86400000); // Duration crystal abonement
        booleanICodec.encode(buf, true); // has double crystal
        buf.writeInt(rank.maxExperience);
        buf.writeInt(1); // place
        buf.writeByte(rank.getNumber()); // why +1 lol
        buf.writeInt(1777); // rating
        buf.writeInt(experience);
        buf.writeInt(1); // Server ID

        stringICodec.encode(buf, nickname);
        stringICodec.encode(buf, "http://ratings.generaltanks.com/pt_br/user/" + nickname);

        controller.sendPacket(PacketId.SetProfileInfo, buf);
        buf.release();
    }

    public void sendEmailInfo() {
        ByteBuf buf = Unpooled.buffer();

        ICodec<Boolean> booleanICodec = CodecRegistry.getCodec(Boolean.class);
        ICodec<String> stringICodec = CodecRegistry.getCodec(String.class);

        stringICodec.encode(buf, "protanki-server@luminate.d");
        booleanICodec.encode(buf, true); // is email confirmed

        controller.sendPacket(PacketId.SetEmailInfo, buf);
        buf.release();
    }
}
