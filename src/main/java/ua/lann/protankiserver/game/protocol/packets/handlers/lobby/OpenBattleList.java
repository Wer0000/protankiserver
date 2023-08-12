package ua.lann.protankiserver.game.protocol.packets.handlers.lobby;

import io.netty.buffer.ByteBuf;
import ua.lann.protankiserver.ClientController;
import ua.lann.protankiserver.enums.Layout;
import ua.lann.protankiserver.game.protocol.packets.PacketId;
import ua.lann.protankiserver.game.protocol.packets.handlers.IHandler;
import ua.lann.protankiserver.game.screens.lobby.BattleSelectScreen;
import ua.lann.protankiserver.game.screens.lobby.GarageScreen;
import ua.lann.protankiserver.models.ClientLayout;
import ua.lann.protankiserver.reflection.annotations.PacketHandler;

@PacketHandler(packetId = PacketId.OpenBattleList)
public class OpenBattleList implements IHandler {
    @Override
    public void handle(ClientController channel, ByteBuf buf) {
        ClientLayout currentLayout = channel.getLayout();

        switch (currentLayout.getFront()) {
            case Lobby -> {
                BattleSelectScreen screen = (BattleSelectScreen) channel.getScreenManager().getScreen();
                screen.close();

                channel.loadLayout(Layout.Battle, Layout.Battle);
            }

            case Garage -> {
                GarageScreen screen = (GarageScreen) channel.getScreenManager().getScreen();
                screen.close();

                channel.getScreenManager().setScreen(BattleSelectScreen.class);
            }

            case Battle -> {
                channel.loadLayout(Layout.Lobby, Layout.Battle);
            }
        }
    }
}
