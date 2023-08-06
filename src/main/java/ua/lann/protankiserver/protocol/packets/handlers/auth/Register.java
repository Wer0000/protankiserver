package ua.lann.protankiserver.protocol.packets.handlers.auth;

import io.netty.buffer.ByteBuf;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.lann.protankiserver.ClientController;
import ua.lann.protankiserver.reflection.annotations.PacketHandler;
import ua.lann.protankiserver.orm.HibernateUtils;
import ua.lann.protankiserver.orm.entities.Player;
import ua.lann.protankiserver.protocol.packets.CodecRegistry;
import ua.lann.protankiserver.protocol.packets.PacketId;
import ua.lann.protankiserver.protocol.packets.codec.ICodec;
import ua.lann.protankiserver.protocol.packets.handlers.IHandler;
import ua.lann.protankiserver.screens.auth.AuthorizationScreen;

@PacketHandler(packetId = PacketId.Register)
public class Register implements IHandler {
    private static final Logger logger = LoggerFactory.getLogger(Register.class);

    @Override
    public void handle(ClientController channel, ByteBuf buf) {
        ICodec<String> stringICodec = CodecRegistry.getCodec(String.class);
        ICodec<Boolean> booleanICodec = CodecRegistry.getCodec(Boolean.class);

        String nickname = stringICodec.decode(buf);
        String password = stringICodec.decode(buf);
        boolean remember = booleanICodec.decode(buf);

        Player player = null;
        try(Session session = HibernateUtils.session()) {
            player = session.get(Player.class, nickname);
            if (player != null) {
                channel.alert("Stop using hacks, bro. This nickname is busy.");
                return;
            }

            session.beginTransaction();

            try {
                player = new Player(nickname);
                player.setPassword(password);

                logger.info("Registering Player[ nickname={}, password={} ]", nickname, player.getPassword());

                session.persist(player);
                session.getTransaction().commit();
            } catch (Exception e) {
                session.getTransaction().rollback();
                logger.error("Failed to register player:", e);
            }

            if (player == null) {
                channel.alert("Failed to register, try again later");
                return;
            }

            AuthorizationScreen screen = (AuthorizationScreen) channel.getScreenManager().getScreenInstance(AuthorizationScreen.class);
            screen.authorize(player, password);
        }
    }
}
