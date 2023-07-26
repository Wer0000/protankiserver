package ua.lann.protankiserver.protocol.packets;

import ua.lann.protankiserver.protocol.packets.PacketId;
import ua.lann.protankiserver.protocol.packets.handlers.EncryptionInitialized;
import ua.lann.protankiserver.protocol.packets.handlers.IHandler;
import ua.lann.protankiserver.protocol.packets.handlers.Pong;
import ua.lann.protankiserver.protocol.packets.handlers.ResourcesLoaded;

import java.util.HashMap;
import java.util.Map;

public class PacketHandlersRegistry {
    private static final Map<PacketId, IHandler> registry = new HashMap<>();

    static {
        registerHandler(PacketId.Pong, new Pong());
        registerHandler(PacketId.CryptoInitialized, new EncryptionInitialized());
        registerHandler(PacketId.ResourcesLoaded, new ResourcesLoaded());

    }

    public static void registerHandler(PacketId alias, IHandler codec) {
        registry.put(alias, codec);
    }
    public static IHandler getHandler(PacketId alias) {
        return registry.get(alias);
    }
}
