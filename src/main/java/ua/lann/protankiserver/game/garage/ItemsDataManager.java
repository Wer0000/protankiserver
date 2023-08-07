package ua.lann.protankiserver.game.garage;

import com.google.gson.JsonObject;
import ua.lann.protankiserver.enums.WeaponType;
import ua.lann.protankiserver.models.garage.data.ItemDataModel;
import ua.lann.protankiserver.util.JsonUtils;

import java.util.HashMap;

public class ItemsDataManager {
    private static final HashMap<WeaponType, HashMap<Integer, ItemDataModel>> weaponsData = new HashMap<>();

    public static ItemDataModel getWeaponData(WeaponType weapon, int modification) {
        return weaponsData.get(weapon).get(modification);
    }

    public static void load() {
        JsonObject object = JsonUtils.readJsonObject("data/weapons.json");

        for (String id : object.keySet()) {
            HashMap<Integer, ItemDataModel> innerHashmap = new HashMap<>();

            JsonObject inner = object.getAsJsonObject(id);
            for (int modification : inner.keySet().stream().map(Integer::parseInt).toList()) {
                ItemDataModel model = new ItemDataModel();
                JsonObject data = inner.getAsJsonObject(String.valueOf(modification));

                model.setObject3ds(data.get("object3ds").getAsInt());
                model.setBaseItemId(data.get("baseItemId").getAsInt());
                model.setPreviewResourceId(data.get("previewResourceId").getAsInt());
                model.setProperties(GarageItemPropertyConverter.convertJsonObject(data.getAsJsonObject("propers")));

                innerHashmap.put(modification, model);
            }

            weaponsData.put(WeaponType.getById(id), innerHashmap);
        }
    }
}
