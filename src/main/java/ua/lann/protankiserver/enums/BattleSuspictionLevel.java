package ua.lann.protankiserver.enums;

public enum BattleSuspictionLevel {
    None(0),
    Low(1),
    High(2);

    public final int id;
    BattleSuspictionLevel(int id) {
        this.id = id;
    }
}
