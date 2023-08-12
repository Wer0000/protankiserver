package ua.lann.protankiserver.enums;

public enum PlayerPermissions {
    Root,
    Spectator,

    AddExperience,
    AddCrystals;

    public int getBit() {
        return 1 + ordinal();
    }
}
