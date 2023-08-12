package ua.lann.protankiserver.game.garage;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import lombok.Getter;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.lann.protankiserver.ClientController;
import ua.lann.protankiserver.enums.GarageItemType;
import ua.lann.protankiserver.game.protocol.packets.CodecRegistry;
import ua.lann.protankiserver.game.protocol.packets.PacketId;
import ua.lann.protankiserver.models.garage.*;
import ua.lann.protankiserver.orm.HibernateUtils;
import ua.lann.protankiserver.orm.entities.Player;
import ua.lann.protankiserver.orm.entities.garage.BaseGarageItem;
import ua.lann.protankiserver.orm.entities.garage.OwnedGarageItem;
import ua.lann.protankiserver.orm.entities.garage.items.EquipmentGarageItem;
import ua.lann.protankiserver.orm.entities.garage.items.PaintGarageItem;
import ua.lann.protankiserver.serialization.JsonUtils;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class GarageManager {
    private static final Logger logger = LoggerFactory.getLogger(GarageManager.class);

    @Getter private static final List<GarageItem> items = new ArrayList<>();
    @Getter private final ClientController controller;

    public GarageManager(ClientController controller) {
        this.controller = controller;
    }


    public OwnedGarageItem getOwnedItem(String itemId) {
        return controller.getPlayer().getOwnedItems().stream()
            .filter(x -> x.getItemId().equals(itemId))
            .findFirst().orElse(null);
    }

    public boolean hasItem(String itemId) {
        return getOwnedItem(itemId) != null;
    }

    public void buyItem(String id, int mod, int amount, int value) {
        logger.info("Attempt buying item {}_m{} x{} ({})", id, mod, amount, value);

        GarageItem targetItem = getGarageItem(id, mod);
        if(targetItem == null) {
            logger.warn("Missing item {}_m{} in registry", id, mod);
            return;
        }

        if(targetItem.isEquippable() && hasItem(id)) targetItem = getGarageItem(id, mod + 1);
        if(!targetItem.getType().equals(GarageItemType.Supply)) amount = 1;

        int actualPrice = targetItem.getPrice() * amount;
        if(value != actualPrice) {
            logger.warn("Client side price mismatch: {} vs {}", actualPrice, value);
            return;
        }

        Player player = controller.getPlayer();

        if(player.getCrystals() < actualPrice) {
            logger.info("Not enough crystals: {} of {}", player.getCrystals(), actualPrice);
            return;
        }

        OwnedGarageItem ownedItem = getOwnedItem(id);

        controller.removeCrystals(actualPrice);
        player.getOwnedItems().remove(ownedItem);
        player.getOwnedItems().add(new OwnedGarageItem(id, targetItem.getModificationID()));

        logger.info("Bought item {} for player {}", id + "_m" + targetItem.getModificationID(), player.getNickname());

        if(targetItem.isEquippable()) setEquipped(id);
    }

    public void tryFitEquip(String itemId) {
        ByteBuf buffer = Unpooled.buffer();

        CodecRegistry.getCodec(String.class).encode(buffer, itemId);
        CodecRegistry.getCodec(Boolean.class).encode(buffer, true);

        controller.sendPacket(PacketId.SetGarageItemEquipped, buffer);
        buffer.release();
    }

    public void setEquipped(String itemId) {
        Optional<OwnedGarageItem> optional = controller.getPlayer().getOwnedItems().stream()
            .filter(x -> x.getItemId().equals(itemId))
            .max(Comparator.comparingInt(OwnedGarageItem::getModification));

        if(optional.isEmpty()) return;

        OwnedGarageItem item = optional.get();
        GarageItem garageItem = items.stream().filter(x -> x.getId().equals(item.getItemId())).findFirst().get();

        if(garageItem.getType().equals(GarageItemType.Weapon)) controller.getPlayer().getEquipment().setWeapon(itemId);
        if(garageItem.getType().equals(GarageItemType.Hull)) controller.getPlayer().getEquipment().setHull(itemId);
        if(garageItem.getType().equals(GarageItemType.Paint)) controller.getPlayer().getEquipment().setPaint(itemId);

        ByteBuf buffer = Unpooled.buffer();
        CodecRegistry.getCodec(String.class).encode(buffer, item.getItemId() + "_m" + item.getModification());
        CodecRegistry.getCodec(Boolean.class).encode(buffer, true);

        controller.sendPacket(PacketId.SetGarageItemEquipped, buffer);
        buffer.release();
    }

    public void sendOwnedItems() {
        InitGarageOwnedItems model = new InitGarageOwnedItems();

        model.setItems(
            GarageManager.getItems().stream()
                .filter(x ->
                    controller.getPlayer().getOwnedItems().stream()
                        .anyMatch(y -> {
                            boolean result = y.getItemId().equals(x.getId());
                            if(x.getType().equals(GarageItemType.Weapon) || x.getType().equals(GarageItemType.Hull)) result &= y.getModification() == x.getModificationID();

                            return result;
                        })
                ).map(x -> {
                    GarageItem item = x.clone();
                    item.setName(x.getLocalizedData().getName(controller.getLocale()));
                    item.setDescription(x.getLocalizedData().getDescription(controller.getLocale()));
                    return item;
                }).toList()
        );

        ByteBuf buffer = Unpooled.buffer();
        CodecRegistry.getCodec(String.class).encode(buffer, JsonUtils.toString(model, InitGarageOwnedItems.class));

        controller.sendPacket(PacketId.InitGarageOwnedItems, buffer);
        buffer.release();
    }

    public void sendBuyableItems() {
        InitGarageBuyableItems model = new InitGarageBuyableItems();
        model.setItems(getItems().stream()
            .filter(x -> !controller.getPlayer().getOwnedItems().contains(x))
            .map(x -> {
                GarageItem item = x.clone();
                item.setName(x.getLocalizedData().getName(controller.getLocale()));
                item.setDescription(x.getLocalizedData().getDescription(controller.getLocale()));
                return item;
            }).toList()
        );

        ByteBuf buffer = Unpooled.buffer();
        CodecRegistry.getCodec(String.class).encode(buffer, JsonUtils.toString(model, InitGarageBuyableItems.class));

        controller.sendPacket(PacketId.InitGarageBuyableItems, buffer);
        buffer.release();
    }

    public static void loadItems() {
        try(Session session = HibernateUtils.session()) {
            Query<EquipmentGarageItem> queryEquip = session.createQuery("FROM EquipmentGarageItem ", EquipmentGarageItem.class);
            List<EquipmentGarageItem> equipmentGarageItems = queryEquip.getResultList();

            for(EquipmentGarageItem item : equipmentGarageItems) {
                GarageItem _item = getGarageItem(item);

                _item.setModificationID(item.getModification());
                _item.setObject3ds(item.getObject3ds());

                _item.setProperts(item.getProperties());

                items.add(_item);
            }

            Query<PaintGarageItem> queryPaint = session.createQuery("FROM PaintGarageItem ", PaintGarageItem.class);
            List<PaintGarageItem> paintGarageItems = queryPaint.getResultList();

            for(PaintGarageItem item : paintGarageItems) {
                GarageItem _item = getGarageItem(item);

                _item.setColoring(item.getColoring());
                _item.setResistances(new PaintResistances(item.getResistances()));

                _item.setProperts(_item.getResistances().toProps());

                items.add(_item);
            }
        }
    }

    public GarageItem getGarageItem(String id, int mod) {
        return items.stream().filter(x ->
            x.getId().equals(id) && x.getModificationID().equals(mod)
        ).findFirst().orElse(null);
    }

    private static GarageItem getGarageItem(BaseGarageItem item) {
        GarageItem _item = new GarageItem();

        _item.setId(item.getId());

        _item.setIndex(item.getItemIndex());
        _item.setType(item.getType());
        _item.setCategory(item.getCategory());

        _item.setRank(item.getRank());
        _item.setNextRank(item.getNextRank());

        _item.setLocalizedData(item.getLocalizedData());

        _item.setPrice(item.getPrice());
        _item.setNextPrice(item.getNextPrice());

        Discount discount = new Discount();
        discount.setPercent(item.getDiscount().getPercent());
        discount.setTimeLeftInSeconds(item.getDiscount().getTimeLeftInSeconds());
        discount.setTimeToStartInSeconds(item.getDiscount().getTimeToStartInSeconds());

        _item.setDiscount(discount);

        _item.setBaseItemId(item.getBaseItemId());
        _item.setPreviewResourceId(item.getPreviewResourceId());

        _item.setRemainingTimeInSeconds(item.getRemainingTimeInSeconds());

        _item.setProperties(item.getProperties());

        return _item;
    }
}
