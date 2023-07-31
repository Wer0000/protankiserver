package ua.lann.protankiserver.enums;

public enum Layout {
    Lobby,
    Garage,
    Payment,
    Battle,
    ReloadSpace;

    public int getId() { return ordinal(); }
}
