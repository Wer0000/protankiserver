package ua.lann.protankiserver;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import lombok.Getter;
import org.hibernate.Session;
import ua.lann.protankiserver.models.friends.FriendsListModel;
import ua.lann.protankiserver.orm.HibernateUtils;
import ua.lann.protankiserver.orm.entities.FriendRequest;
import ua.lann.protankiserver.orm.entities.Player;
import ua.lann.protankiserver.protocol.packets.CodecRegistry;
import ua.lann.protankiserver.protocol.packets.PacketId;
import ua.lann.protankiserver.protocol.packets.codec.ICodec;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public final class FriendsManager {
    @Getter
    private final ClientController controller;

    @Getter private FriendsListModel model;

    public FriendsManager(ClientController controller) {
        this.controller = controller;
        this.model = new FriendsListModel();
    }

    public void loadFriendsList() {
        try (Session session = HibernateUtils.session()) {
            ByteBuf buf = Unpooled.buffer();

            Player player = session.get(Player.class, controller.getPlayer().getNickname());

            for (Player friend : player.getFriends()) {
                model.getFriendsAccepted().add(friend.getNickname());
            }

            for (FriendRequest req : player.getIncomingFriendRequests()) {
                model.getFriendsIncoming().add(req.getSender().getNickname());
            }

            for (FriendRequest req : player.getOutgoingFriendRequests()) {
                model.getFriendsOutgoing().add(req.getReceiver().getNickname());
            }

            List<List<String>> keys = Arrays.asList(
                    model.getFriendsAccepted(),
                    model.getFriendsAcceptedNew(),
                    model.getFriendsIncoming(),
                    model.getFriendsIncomingNew(),
                    model.getFriendsOutgoing()
            );

            ICodec<String> stringICodec = CodecRegistry.getCodec(String.class);
            for (List<String> nicknames : keys) {
                buf.writeByte(0);
                buf.writeInt(nicknames.size());
                for (String nickname : nicknames) stringICodec.encode(buf, nickname);
            }

            controller.sendPacket(PacketId.LoadFriendsList, buf);
            buf.release();
        }
    }

    public boolean sendFriendRequest(String targetNickname) {
        try (Session session = HibernateUtils.session()) {
            Player senderPlayer = session.get(Player.class, controller.getPlayer().getNickname());
            Player targetPlayer = session.get(Player.class, targetNickname);

            if (targetPlayer == null) return false;

            FriendRequest request = new FriendRequest();
            request.setReceiver(targetPlayer);
            request.setSender(senderPlayer);

            senderPlayer.getOutgoingFriendRequests().add(request);
            targetPlayer.getIncomingFriendRequests().add(request);

            session.getTransaction().begin();

            session.persist(request);
            session.merge(senderPlayer);
            session.merge(targetPlayer);

            session.getTransaction().commit();

            model.getFriendsOutgoing().add(targetNickname);
            updateFriendsList();

            ClientController targetController = Server.getInstance().tryGetOnlinePlayerController(targetNickname);
            if(targetController == null) return true;

            FriendsManager targetFriendsManager = targetController.getFriendsManager();

            ByteBuf buf = Unpooled.buffer();
            CodecRegistry.getCodec(String.class).encode(buf, controller.getPlayer().getNickname());
            targetController.sendPacket(PacketId.IncomingFriendRequest, buf);
            buf.release();

            targetFriendsManager.getModel().getFriendsIncoming().add(controller.getPlayer().getNickname());
            targetFriendsManager.getModel().getFriendsIncomingNew().add(controller.getPlayer().getNickname());
            targetFriendsManager.updateFriendsList();
            return true;
        }
    }

    public void updateFriendsList() {
        // TODO
    }

    private static boolean processRequest(Session session, FriendRequest request, Player sender, Player target) {
        session.getTransaction().begin();
        session.merge(sender);
        session.merge(target);
        session.getTransaction().commit();

        session.getTransaction().begin();
        session.remove(request);
        session.getTransaction().commit();
        return true;
    }

    public static void acceptFriendRequest(Session session, FriendRequest request) {
        Player sender = request.getSender();
        Player target = request.getReceiver();

        sender.getOutgoingFriendRequests().remove(request);
        target.getIncomingFriendRequests().remove(request);

        sender.getFriends().add(target);
        target.getFriends().add(sender);

        ClientController senderController = Server.getInstance().tryGetOnlinePlayerController(sender.getNickname());
        ClientController targetController = Server.getInstance().tryGetOnlinePlayerController(target.getNickname());

        ByteBuf buf = Unpooled.buffer();

        CodecRegistry.getCodec(String.class).encode(buf, target.getNickname());
        if(senderController != null) senderController.sendPacket(PacketId.FriendRequestAccepted, buf);
        buf.clear();

        CodecRegistry.getCodec(String.class).encode(buf, sender.getNickname());
        if(targetController != null) targetController.sendPacket(PacketId.FriendRequestAccepted, buf);
        buf.release();

        processRequest(session, request, sender, target);
    }

    public static void rejectFriendRequest(Session session, FriendRequest request) {
        Player sender = request.getSender();
        Player target = request.getReceiver();

        sender.getOutgoingFriendRequests().remove(request);
        target.getIncomingFriendRequests().remove(request);

        processRequest(session, request, sender, target);
    }

    public ClientController controller() {
        return controller;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (FriendsManager) obj;
        return Objects.equals(this.controller, that.controller);
    }

    @Override
    public int hashCode() {
        return Objects.hash(controller);
    }

    @Override
    public String toString() {
        return "FriendsManager[" +
                "controller=" + controller + ']';
    }

}
