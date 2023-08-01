package ua.lann.protankiserver.models;

import lombok.Getter;
import lombok.Setter;
import ua.lann.protankiserver.enums.Layout;

@Getter @Setter
public class ClientLayout {
    public Layout front;
    public Layout back;
}
