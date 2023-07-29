package ua.lann.protankiserver.protocol.packets;

import ua.lann.protankiserver.protocol.packets.handlers.RequestCaptcha;
import ua.lann.protankiserver.protocol.packets.handlers.auth.Register;
import ua.lann.protankiserver.protocol.packets.handlers.auth.RegisterVerifyUsername;
import ua.lann.protankiserver.protocol.packets.handlers.base.EncryptionInitialized;
import ua.lann.protankiserver.protocol.packets.handlers.IHandler;
import ua.lann.protankiserver.protocol.packets.handlers.base.Pong;
import ua.lann.protankiserver.protocol.packets.handlers.base.ResourcesLoaded;

import java.util.HashMap;
import java.util.Map;

public class PacketHandlersRegistry {
    private static final Map<PacketId, IHandler> registry = new HashMap<>();

    static {
        registerHandler(PacketId.Pong, new Pong());
        registerHandler(PacketId.CryptoInitialized, new EncryptionInitialized());
        registerHandler(PacketId.ResourcesLoaded, new ResourcesLoaded());

        registerHandler(PacketId.RequestCaptcha, new RequestCaptcha());

        registerHandler(PacketId.Register, new Register());
        registerHandler(PacketId.RegisterVerifyUsername, new RegisterVerifyUsername());
    }

    public static void registerHandler(PacketId alias, IHandler codec) {
        registry.put(alias, codec);
    }
    public static IHandler getHandler(PacketId alias) {
        return registry.get(alias);
    }
}
