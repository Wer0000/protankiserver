package ua.lann.protankiserver.game.resources;

public enum ResourcesPack {
    Main(1),
    Garage(2);

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
