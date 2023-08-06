package ua.lann.protankiserver.game.protocol.packets;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.lann.protankiserver.reflection.AnnotationHelper;
import ua.lann.protankiserver.reflection.annotations.PacketHandler;
import ua.lann.protankiserver.game.protocol.packets.handlers.IHandler;

import java.util.*;

public class PacketHandlersRegistry {
    private static final Logger logger = LoggerFactory.getLogger(PacketHandlersRegistry.class);
    private static final Map<PacketId, IHandler> registry = new HashMap<>();

    public static void load() {
        try {
            List<Class<?>> annotatedClasses = AnnotationHelper.load(PacketHandler.class, "ua.lann.protankiserver.game.protocol.packets.handlers");
            for (Class<?> annotatedClass : annotatedClasses) {
                if(!IHandler.class.isAssignableFrom(annotatedClass)) return;

                PacketHandler annotation = annotatedClass.getAnnotation(PacketHandler.class);
                if(annotation == null) return;

                IHandler handler = (IHandler) annotatedClass.getDeclaredConstructor().newInstance();
                registry.put(annotation.packetId(), handler);

                logger.info("Registered packet handler for {}", annotation.packetId());
            }
        } catch (Exception e) {
            logger.error("Error while loading handlers:", e);
        }
    }

    public static IHandler getHandler(PacketId alias) {
        return registry.get(alias);
    }
}
