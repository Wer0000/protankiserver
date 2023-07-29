package ua.lann.protankiserver.enums;

public enum Achievement {
    FirstRankUp,
    FirstPurchase,
    SetEmail,
    FightFirstBattle,
    FirstDonate;

    public int getId() { return ordinal(); }
}
