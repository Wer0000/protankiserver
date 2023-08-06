package ua.lann.protankiserver.game.protocol.packets;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.lann.protankiserver.game.protocol.packets.codec.ICodec;
import ua.lann.protankiserver.reflection.AnnotationHelper;
import ua.lann.protankiserver.reflection.annotations.Codec;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CodecRegistry {
    private static final Logger logger = LoggerFactory.getLogger(CodecRegistry.class);
    private static final Map<Class<?>, ICodec<?>> registry = new HashMap<>();

    public static void load() {
        try {
            List<Class<?>> annotatedClasses = AnnotationHelper.load(Codec.class, "ua.lann.protankiserver.game.protocol.packets.codec");
            for (Class<?> annotatedClass : annotatedClasses) {
                Codec annotation = annotatedClass.getAnnotation(Codec.class);
                if(annotation == null) return;

                ICodec handler = (ICodec) annotatedClass.getDeclaredConstructor().newInstance();
                registry.put(annotation.type(), handler);

                logger.info("Registered codec for {}", annotation.type());
            }
        } catch (Exception e) {
            logger.error("Error while loading codecs:", e);
        }
    }

    public static <T> ICodec<T> getCodec(Class<T> alias) {
        return (ICodec<T>) registry.get(alias);
    }
}