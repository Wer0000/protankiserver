package ua.lann.protankiserver.enums;

public enum Achievement {
    FirstRankUp(0),
    FirstPurchase(1),
    SetEmail(2),
    FightFirstBattle(3),
    FirstDonate(4);

    public final int id;
    Achievement(int id) {
        this.id = id;
    }
}
