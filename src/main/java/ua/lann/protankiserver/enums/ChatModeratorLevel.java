package ua.lann.protankiserver.enums;

public enum ChatModeratorLevel {
    None(0),
    CommunityManager(1),
    Administrator(2),
    Moderator(3),
    Candidate(4);

    public final int id;
    ChatModeratorLevel(int id) {
        this.id = id;
    }
}
