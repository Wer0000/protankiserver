package ua.lann.protankiserver.localization;

public enum Locale {
    Russian,
    English;

    public static Locale fromString(String locale) {
        switch (locale) {
            case "ru" -> { return Russian; }
            case "en" -> { return English; }
        }

        return Locale.English;
    }
}
