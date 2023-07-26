package ua.lann.protankiserver.resources;

import ua.lann.protankiserver.protocol.packets.PacketId;

public enum ResourcesPack {
    Main(1);

    public final int callbackId;
    ResourcesPack(int callbackId) {
        this.callbackId = callbackId;
    }

    public static ResourcesPack getByCallbackId(int callbackId) {
        for (ResourcesPack enumValue : values()) {
            if (enumValue.callbackId == callbackId) {
                return enumValue;
            }
        }

        return null;
    }
}
