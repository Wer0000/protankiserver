package ua.lann.protankiserver.game.protocol.packets.handlers.lobby;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import ua.lann.protankiserver.ClientController;
import ua.lann.protankiserver.enums.Layout;
import ua.lann.protankiserver.game.garage.GarageManager;
import ua.lann.protankiserver.game.protocol.packets.PacketId;
import ua.lann.protankiserver.game.protocol.packets.handlers.IHandler;
import ua.lann.protankiserver.game.resources.ResourcesPack;
import ua.lann.protankiserver.models.garage.InitGarageOwnedItems;
import ua.lann.protankiserver.orm.entities.garage.EquippedTankData;
import ua.lann.protankiserver.orm.entities.garage.items.EquipmentGarageItem;
import ua.lann.protankiserver.reflection.annotations.PacketHandler;

@PacketHandler(packetId = PacketId.OpenGarage)
public class OpenGarage implements IHandler {
    @Override
    public void handle(ClientController channel, ByteBuf buf) {
        channel.sendPacket(PacketId.OpenGarageResponse, Unpooled.buffer());
        channel.getResourcesManager().load(ResourcesPack.Garage, () -> {
            InitGarageOwnedItems owned = GarageManager.sendOwnedItems(channel);
            GarageManager.sendBuyableItems(owned.getItems(), channel);

            EquippedTankData equipment = channel.getPlayer().getEquipment();
            GarageManager.setEquipped(channel, equipment.getWeapon());
            GarageManager.setEquipped(channel, equipment.getHull());
            GarageManager.setEquipped(channel, equipment.getPaint());

            channel.loadLayout(Layout.Garage, Layout.Garage);
        });
    }
}
