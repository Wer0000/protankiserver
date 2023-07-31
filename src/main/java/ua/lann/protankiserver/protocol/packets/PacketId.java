package ua.lann.protankiserver.protocol.packets;

public enum PacketId {
    InitializeCrypto(2001736388),
    CryptoInitialized(-1864333717),

    Ping(-555602629),
    Pong(1484572481),

    MessageAlert(-600078553),

    InitLoginSocialButtons(-1715719586),
    InitCaptchaPositions(321971701),
    RequireInviteCode(444933603),
    InitLoginPage(-1277343167),
    RemoveLoading(-1282173466),

    Register(427083290),
    RegisterVerifyUsername(1083705823),
    UsernameAvailable(-706679202),
    RecommendedNames(442888643),

    RemoveLoginForm(-1923286328),
    InvalidCredentials(103812952),
    Login(-739684591),

    RequestCaptcha(-349828108),
    SetCaptcha(-1670408519),

    LoadResources(-1797047325),
    LoadSingleResource(834877801),
    ResourcesLoaded(-82304134),

    SetPremiumInfo(1405859779),
    SetProfileInfo(907073245),
    SetEmailInfo(613462801),
    InitAchievements(602656160),

    ConfirmLayoutAccessible(1118835050),
    SetLayout(-593368100),

    ConfigureLobbyChat(178154988),
    SetLobbyChatDelay(744948472),
    LoadMessagesHistory(-1263520410),
    LobbyChatSendMessage(705454610);

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
