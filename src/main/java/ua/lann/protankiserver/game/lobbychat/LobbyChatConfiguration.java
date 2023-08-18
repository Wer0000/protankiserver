package ua.lann.protankiserver.game.lobbychat;

public class LobbyChatConfiguration {
    public static boolean Enabled = true;
    public static final boolean ShowLinks = true;
    public static final int SymbolCost = 176;
    public static final int EnterCost = 880;

    public static class Antiflood {
        public static final boolean Enabled = false;
        public static final boolean TypingSpeedAntifloodEnabled = false;
    }
}
