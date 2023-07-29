package ua.lann.protankiserver.protocol.packets.handlers;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import ua.lann.protankiserver.ClientController;
import ua.lann.protankiserver.captcha.Captcha;
import ua.lann.protankiserver.enums.CaptchaLocation;
import ua.lann.protankiserver.protocol.packets.PacketId;

import java.util.Base64;

public class RequestCaptcha implements IHandler {
    @Override
    public void handle(ClientController channel, ByteBuf buf) {
        CaptchaLocation location = CaptchaLocation.values()[buf.readInt()];
        String captcha = Captcha.getCaptcha(location);

        ByteBuf buffer = Unpooled.buffer();
        buffer.writeInt(location.getId());

        byte[] decodedCaptcha = Base64.getDecoder().decode(captcha);
        buffer.writeInt(decodedCaptcha.length);
        buffer.writeBytes(decodedCaptcha);

        channel.sendPacket(PacketId.SetCaptcha, buffer);
        buffer.release();
    }
}
