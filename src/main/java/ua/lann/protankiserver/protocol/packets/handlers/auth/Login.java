package ua.lann.protankiserver.protocol.packets.handlers.auth;

import io.netty.buffer.ByteBuf;
import org.hibernate.Session;
import ua.lann.protankiserver.ClientController;
import ua.lann.protankiserver.reflection.annotations.PacketHandler;
import ua.lann.protankiserver.orm.HibernateUtils;
import ua.lann.protankiserver.orm.entities.Player;
import ua.lann.protankiserver.protocol.packets.CodecRegistry;
import ua.lann.protankiserver.protocol.packets.PacketId;
import ua.lann.protankiserver.protocol.packets.codec.ICodec;
import ua.lann.protankiserver.protocol.packets.handlers.IHandler;
import ua.lann.protankiserver.screens.auth.AuthorizationScreen;

@PacketHandler(packetId = PacketId.Login)
public class Login implements IHandler {
    @Override
    public void handle(ClientController channel, ByteBuf buf) {
        ICodec<String> stringICodec = CodecRegistry.getCodec(String.class);
        ICodec<Boolean> booleanICodec = CodecRegistry.getCodec(Boolean.class);
        
        AuthorizationScreen screen = (AuthorizationScreen) channel.getScreenManager().getScreenInstance(AuthorizationScreen.class);

        String nickname = stringICodec.decode(buf);
        String password = stringICodec.decode(buf);
        boolean rememberMe = booleanICodec.decode(buf);

        try(Session session = HibernateUtils.session()) {
            Player player = session.get(Player.class, nickname);
            if(player == null) {
                screen.invalidCredentials();
                return;
            }

            screen.authorize(player, password);
        }
    }
}
