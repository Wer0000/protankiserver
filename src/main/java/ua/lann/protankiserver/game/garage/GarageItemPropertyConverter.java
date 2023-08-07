package ua.lann.protankiserver.game.garage;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import ua.lann.protankiserver.models.garage.GarageItemProperty;

import java.util.*;

public class GarageItemPropertyConverter {

    public static List<GarageItemProperty> convertJsonObject(JsonObject jsonObject) {
        List<GarageItemProperty> garageItemProperties = new ArrayList<>();
        List<String> archived = new ArrayList<>();

        for (String property : jsonObject.keySet()) {
            JsonObject propertyObject = jsonObject.get(property).getAsJsonObject();
            JsonArray subpropertiesArray = propertyObject.get("subproperties").isJsonNull() ? null : propertyObject.getAsJsonArray("subproperties");

            if(archived.contains(property)) continue;

            GarageItemProperty prop = new GarageItemProperty();
            prop.setProperty(property);

            if(subpropertiesArray != null) {
                prop.setValue(null);
                prop.setSubproperties(new ArrayList<>());

                for(JsonElement keyElement : subpropertiesArray) {
                    String key = keyElement.getAsString();
                    JsonObject propertyObjectInner = jsonObject.getAsJsonObject(key);

                    GarageItemProperty prop2 = new GarageItemProperty();
                    prop2.setProperty(key);
                    prop2.setValue(propertyObjectInner.get("value").getAsDouble());
                    prop.getSubproperties().add(prop2);

                    archived.add(key);
                }

            } else {
                prop.setValue(propertyObject.get("value").getAsDouble());
                prop.setSubproperties(null);
            }

            garageItemProperties.add(prop);
        }

        return garageItemProperties;
    }
}
