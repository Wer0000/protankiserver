package ua.lann.protankiserver.protocol.packets.handlers;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import ua.lann.protankiserver.ClientController;
import ua.lann.protankiserver.ServerSettings;
import ua.lann.protankiserver.protocol.packets.CodecRegistry;
import ua.lann.protankiserver.protocol.packets.PacketId;
import ua.lann.protankiserver.protocol.packets.codec.ICodec;
import ua.lann.protankiserver.resources.ResourcesPack;

public class EncryptionInitialized implements IHandler {
    private final ICodec<String> stringCodec = CodecRegistry.getCodec(String.class);

    @Override
    public void handle(ClientController channel, ByteBuf buf) {
        channel.sendPacket(PacketId.Ping, Unpooled.buffer());
        channel.setLocale(stringCodec.decode(buf));

        ByteBuf buffer = Unpooled.buffer();
        ICodec<String> stringCodec = CodecRegistry.getCodec(String.class);
        ICodec<Boolean> boolCodec = CodecRegistry.getCodec(Boolean.class);

        stringCodec.encode(buffer, "https://oauth.vk.com/authorize?client_id=7889475&response_type=code&display=page&redirect_uri=http://146.59.110.195:8090/externalEntrance/vkontakte/?session=-1753613718684519995");
        stringCodec.encode(buffer, "vkontakte");

        channel.sendPacket(PacketId.InitLoginSocialButtons, buffer);
        buffer.clear();

        buffer.writeInt(0);
        channel.sendPacket(PacketId.InitCaptchaPositions, buffer);

        channel.resources.load(ResourcesPack.Main, (callbackId) -> {
            buffer.clear();
            boolCodec.encode(buffer, ServerSettings.InviteOnly);
            channel.sendPacket(PacketId.RequireInviteCode, buffer);

            buffer.clear();
            buffer.writeInt(ServerSettings.Auth.LoginScreenBackground);
            boolCodec.encode(buffer, ServerSettings.Auth.emailRequired);
            buffer.writeInt(ServerSettings.Auth.maxLength);
            buffer.writeInt(ServerSettings.Auth.minLength);
            channel.sendPacket(PacketId.InitLoginPage, buffer);

            buffer.clear();
            channel.sendPacket(PacketId.RemoveLoading, buffer);
        });
    }
}
