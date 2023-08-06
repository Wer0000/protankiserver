package ua.lann.protankiserver.reflection.annotations;

import ua.lann.protankiserver.game.protocol.packets.PacketId;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface PacketHandler {
    PacketId packetId();
}
