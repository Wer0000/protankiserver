package ua.lann.protankiserver.game.screens.lobby;

import io.netty.buffer.Unpooled;
import ua.lann.protankiserver.ClientController;
import ua.lann.protankiserver.enums.Layout;
import ua.lann.protankiserver.game.garage.GarageManager;
import ua.lann.protankiserver.game.protocol.packets.PacketId;
import ua.lann.protankiserver.game.resources.ResourcesPack;
import ua.lann.protankiserver.game.screens.ScreenBase;
import ua.lann.protankiserver.orm.entities.garage.EquippedTankData;

public class GarageScreen extends ScreenBase {
    public GarageScreen(ClientController controller) {
        super(controller);
    }

    @Override
    public void open() {
        controller.sendPacket(PacketId.OpenGarageResponse, Unpooled.buffer());
        controller.getResourcesManager().load(ResourcesPack.Garage, () -> {
            GarageManager garage = controller.getGarageManager();
            garage.sendOwnedItems();
            garage.sendBuyableItems();

            EquippedTankData equipment = controller.getPlayer().getEquipment();
            garage.setEquipped(equipment.getWeapon());
            garage.setEquipped(equipment.getHull());
            garage.setEquipped(equipment.getPaint());

            controller.loadLayout(Layout.Garage, Layout.Garage);
        });
    }

    @Override
    public void close() {
        controller.sendPacket(PacketId.CloseGarage, Unpooled.buffer());
    }
}
