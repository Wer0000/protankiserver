package ua.lann.protankiserver.enums;

public enum BattleMode {
    DM,
    TDM,
    CTF,
    CP,
    AS;

    public int getId() { return ordinal(); }
}
