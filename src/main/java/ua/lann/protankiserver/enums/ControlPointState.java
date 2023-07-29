package ua.lann.protankiserver.enums;

public enum ControlPointState {
    Red,
    Blue,
    Neutral;

    public int getId() {
        return ordinal();
    }
}
