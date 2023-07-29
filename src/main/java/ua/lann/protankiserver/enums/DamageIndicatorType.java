package ua.lann.protankiserver.enums;

public enum DamageIndicatorType {
    Normal(0),
    Critical(1),
    Fatal(2),
    Heal(3);

    public final int id;
    DamageIndicatorType(int id) {
        this.id = id;
    }
}
