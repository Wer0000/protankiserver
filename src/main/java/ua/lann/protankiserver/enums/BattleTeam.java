package ua.lann.protankiserver.enums;

public enum BattleTeam {
    Red,
    Blue,
    None;

    public int getId() { return ordinal(); }
}
