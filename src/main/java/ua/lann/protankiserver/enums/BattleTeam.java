package ua.lann.protankiserver.enums;

public enum BattleTeam {
    Red(0),
    Blue(1),
    None(2);

    public final int id;
    BattleTeam(int id) {
        this.id = id;
    }
}
