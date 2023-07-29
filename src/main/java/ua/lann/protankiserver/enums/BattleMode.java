package ua.lann.protankiserver.enums;

public enum BattleMode {
    DM(0),
    TDM(1),
    CTF(2),
    CP(3),
    AS(4);

    public final int id;
    BattleMode(int id) {
        this.id = id;
    }
}
