package ua.lann.protankiserver.enums;

public enum ControlPointState {
    Red(0),
    Blue(1),
    Neutral(2);

    public final int id;
    ControlPointState(int id) {
        this.id = id;
    }
}
