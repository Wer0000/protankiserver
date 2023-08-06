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

    ToggleShowDamage(-731115522), // Determines if server needs to send damage splashes to client (Setting)
    ConfigureLobbyChat(178154988),
    SetLobbyChatDelay(744948472),
    LoadMessagesHistory(-1263520410),
    LobbyChatSendMessage(705454610),

    InitMapList(-838186985),
    InitBattleList(552006706),

    GetBattleInfo(2092412133),
    SetBattleSelected(-602527073),
    SendBattleInfo(546722394),

    RequestUserInfo(1774907609),
    SetUserOnlineInfo(2041598093),
    SetUserRankInfo(-962759489),
    SetUserPremiumInfo(-2069508071),
    NotifySomethingIDontKnow(1941694508),

    LoadFriendsList(1422563374),
    OpenFriendsList(1441234714),
    SetFriendsListOpened(-437587751),

    VerifyRequestedNicknameExists(126880779),
    RequestedNicknameNotExists(-1490761936),
    RequestedNicknameExists(-707501253),
    SendFriendRequest(-1457773660),

    AlreadyFriends(-2089008699),
    AlreadyHasIncomingRequest(-1258754138),
    AlreadySentFriendRequestToThatPlayer(2064692768),
    RequestSent(-1241704092),
    IncomingFriendRequest(553380510),
    CancelFriendRequest(84050355),
    NotifyFriendRequestCancelled(-1885167992),

    AcceptFriendRequest(-1926185291),
    RejectFriendRequest(-1588006900),
    FriendRequestAccepted(-139645601),

    OpenGarage(-479046431);

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
