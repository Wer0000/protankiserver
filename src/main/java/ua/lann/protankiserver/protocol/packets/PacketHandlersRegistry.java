package ua.lann.protankiserver.protocol.packets;

import ua.lann.protankiserver.protocol.packets.handlers.RequestCaptcha;
import ua.lann.protankiserver.protocol.packets.handlers.auth.Login;
import ua.lann.protankiserver.protocol.packets.handlers.auth.Register;
import ua.lann.protankiserver.protocol.packets.handlers.auth.RegisterVerifyUsername;
import ua.lann.protankiserver.protocol.packets.handlers.base.EncryptionInitialized;
import ua.lann.protankiserver.protocol.packets.handlers.IHandler;
import ua.lann.protankiserver.protocol.packets.handlers.base.Pong;
import ua.lann.protankiserver.protocol.packets.handlers.base.RequestUserInfo;
import ua.lann.protankiserver.protocol.packets.handlers.base.ResourcesLoaded;
import ua.lann.protankiserver.protocol.packets.handlers.lobby.GetBattleInfo;
import ua.lann.protankiserver.protocol.packets.handlers.lobby.LobbyChatSendMessage;
import ua.lann.protankiserver.protocol.packets.handlers.lobby.friends.*;

import java.util.HashMap;
import java.util.Map;

public class PacketHandlersRegistry {
    private static final Map<PacketId, IHandler> registry = new HashMap<>();

    static {
        registerHandler(PacketId.Pong, new Pong());
        registerHandler(PacketId.CryptoInitialized, new EncryptionInitialized());
        registerHandler(PacketId.ResourcesLoaded, new ResourcesLoaded());

        registerHandler(PacketId.RequestCaptcha, new RequestCaptcha());
        registerHandler(PacketId.RegisterVerifyUsername, new RegisterVerifyUsername());
        registerHandler(PacketId.Register, new Register());
        registerHandler(PacketId.Login, new Login());

        registerHandler(PacketId.RequestUserInfo, new RequestUserInfo());

        registerHandler(PacketId.OpenFriendsList, new OpenFriendsList());
        registerHandler(PacketId.VerifyRequestedNicknameExists, new VerifyRequestedNicknameExists());
        registerHandler(PacketId.SendFriendRequest, new SendFriendRequest());
        registerHandler(PacketId.CancelFriendRequest, new CancelFriendRequest());

        registerHandler(PacketId.AcceptFriendRequest, new AcceptFriendRequest());
        registerHandler(PacketId.RejectFriendRequest, new RejectFriendRequest());

        registerHandler(PacketId.LobbyChatSendMessage, new LobbyChatSendMessage());
        registerHandler(PacketId.GetBattleInfo, new GetBattleInfo());
    }

    public static void registerHandler(PacketId alias, IHandler codec) {
        registry.put(alias, codec);
    }
    public static IHandler getHandler(PacketId alias) {
        return registry.get(alias);
    }
}
