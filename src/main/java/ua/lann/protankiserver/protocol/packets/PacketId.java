package ua.lann.protankiserver.protocol.packets;

public enum PacketId {
    InitializeCrypto(2001736388),
    CryptoInitialized(-1864333717),

    Ping(-555602629),
    Pong(1484572481),

    InitLoginSocialButtons(-1715719586),
    InitCaptchaPositions(321971701),
    RequireInviteCode(444933603),
    InitLoginPage(-1277343167),
    RemoveLoading(-1282173466),

    LoadResources(-1797047325),
    ResourcesLoaded(-82304134);

    public final int packetId;

    PacketId(int pid) {
        packetId = pid;
    }

    public static PacketId getByPacketId(int packetId) {
        for (PacketId enumValue : values()) {
            if (enumValue.packetId == packetId) {
                return enumValue;
            }
        }

        return null;
    }
}
