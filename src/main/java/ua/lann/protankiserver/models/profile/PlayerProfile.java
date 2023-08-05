package ua.lann.protankiserver.models.profile;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import lombok.Getter;
import lombok.Setter;
import ua.lann.protankiserver.ClientController;
import ua.lann.protankiserver.enums.ChatModeratorLevel;
import ua.lann.protankiserver.enums.Rank;
import ua.lann.protankiserver.orm.entities.Player;
import ua.lann.protankiserver.protocol.packets.CodecRegistry;
import ua.lann.protankiserver.protocol.packets.PacketId;
import ua.lann.protankiserver.protocol.packets.codec.ICodec;

@Getter
@Setter
public class PlayerProfile {
    private final ClientController controller;

    private String nickname;
    private ChatModeratorLevel chatModeratorLevel;

    private Rank rank;
    private int experience;
    private int crystals;

    private PremiumInfo premiumInfo;


    public PlayerProfile(Player player, ClientController controller) {
        this.controller = controller;

        this.nickname = player.getNickname();

        this.rank = player.getRank();
        this.experience = player.getExperience();
        this.crystals = player.getCrystals();
        this.chatModeratorLevel = player.getLevel();

        this.premiumInfo = new PremiumInfo(
                false,
                false,
                99665.5234375f,
                true,
                false,
                16777235
        );
    }

    public void sendPremiumInfo() {
        ByteBuf buf = Unpooled.buffer();

        ICodec<Boolean> booleanICodec = CodecRegistry.getCodec(Boolean.class);

        booleanICodec.encode(buf, premiumInfo.isNeedShowNotificationCompletionPremium());
        booleanICodec.encode(buf, premiumInfo.isNeedShowWelcomeAlert());
        buf.writeFloat(premiumInfo.getReminderCompletionPremiumTime());
        booleanICodec.encode(buf, premiumInfo.isWasShowAlertForFirstPurchasePremium());
        booleanICodec.encode(buf, premiumInfo.isWasShowReminderCompletionPremium());
        buf.writeInt(premiumInfo.getLifetimeInSeconds());

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
        buf.writeInt(1); // rating
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
