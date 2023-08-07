package ua.lann.protankiserver.models.garage;

import lombok.Getter;
import lombok.Setter;
import ua.lann.protankiserver.reflection.annotations.SerializeIfNull;

import java.util.List;

@Getter @Setter
public class GarageItemProperty {
    private String property;
    @SerializeIfNull private Object value;
    @SerializeIfNull private List<GarageItemProperty> subproperties;
}
