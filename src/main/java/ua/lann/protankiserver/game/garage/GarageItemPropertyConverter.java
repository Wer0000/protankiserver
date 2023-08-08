package ua.lann.protankiserver.game.garage;

import ua.lann.protankiserver.orm.models.GarageItemProperty;
import ua.lann.protankiserver.orm.models.GarageItemRawProperty;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class GarageItemPropertyConverter {
    public static List<GarageItemProperty> convert(Map<String, GarageItemRawProperty> props) {
        List<GarageItemProperty> result = new ArrayList<>();
        if(props.isEmpty()) return null;

        List<String> removal = new ArrayList<>();
        for(String key : props.keySet()) {
            if(removal.contains(key)) continue;

            GarageItemRawProperty rawProperty = props.get(key);
            GarageItemProperty prop = new GarageItemProperty();

            prop.setProperty(key);
            prop.setValue(rawProperty.getValue());
            if(rawProperty.getSubproperties() != null) prop.setSubproperties(
                rawProperty.getSubproperties().stream().map(x -> {
                    GarageItemProperty newProp = new GarageItemProperty();

                    newProp.setProperty(x);
                    newProp.setValue(props.get(x).getValue());
                    newProp.setSubproperties(null);
                    removal.add(x);

                    return newProp;
                }).toList()
            );

            result.add(prop);
        }

        for (String key : removal) {
            props.remove(key);
        }

        return result;
    }
}
