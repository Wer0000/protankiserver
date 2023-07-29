package ua.lann.protankiserver.enums;

public enum BattleSuspictionLevel {
    None,
    Low,
    High;

    public int getId() {
        return ordinal();
    }
}
