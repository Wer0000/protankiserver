package ua.lann.protankiserver.game.garage;

import com.squareup.moshi.Types;
import ua.lann.protankiserver.enums.WeaponType;
import ua.lann.protankiserver.orm.models.*;
import ua.lann.protankiserver.serialization.JsonUtils;

import java.util.HashMap;
import java.util.Map;

public class ItemsDataManager {
    private static final HashMap<WeaponType, HashMap<Integer, GarageItemData>> weaponsData = new HashMap<>();

    public static GarageItemData getWeaponData(WeaponType weapon, int modification) {
        return weaponsData.get(weapon).get(modification);
    }

    public static void load() {
        Map<String, Map<String, GarageItemRawData>> model = JsonUtils.readResource("data/weapons.json", Types.newParameterizedType(
            Map.class,
            String.class,
            Types.newParameterizedType(
                Map.class,
                String.class,
                GarageItemRawData.class
            )
        ));

        for (String id : model.keySet()) {
            HashMap<Integer, GarageItemData> innerHashmap = new HashMap<>();

            Map<String, GarageItemRawData> inner = model.get(id);
            for (int modification : inner.keySet().stream().map(Integer::parseInt).toList()) {
                GarageItemData _model = new GarageItemData();

                GarageItemRawData rawData = inner.get(String.valueOf(modification));

                _model.setObject3ds(rawData.getObject3ds());
                _model.setBaseItemId(rawData.getBaseItemId());
                _model.setPreviewResourceId(rawData.getPreviewResourceId());
                _model.setProperties(rawData.getProperties().entrySet().stream().map(x -> {
                    GarageItemProperty prop = new GarageItemProperty();

                    prop.setProperty(x.getKey());
                    prop.setValue(x.getValue().getValue());
                    if(x.getValue().getSubproperties() != null) prop.setSubproperties(x.getValue().getSubproperties().stream().map(y -> {
                        GarageItemProperty innerProp = new GarageItemProperty();
                        GarageItemRawProperty imod = rawData.getProperties().get(y);

                        innerProp.setProperty(y);
                        innerProp.setValue(imod.getValue());
                        innerProp.setSubproperties(null);

                        return innerProp;
                    }).toList());

                    return prop;
                }).toList());

                innerHashmap.put(modification, _model);
            }

            weaponsData.put(WeaponType.getById(id), innerHashmap);
        }
    }
}
