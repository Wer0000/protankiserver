package ua.lann.protankiserver.enums;

public enum ChatModeratorLevel {
    None,
    CommunityManager,
    Administrator,
    Moderator,
    Candidate;

    public int getId() { return ordinal(); }
}
