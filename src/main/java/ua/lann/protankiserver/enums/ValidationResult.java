package ua.lann.protankiserver.enums;

public enum ValidationResult {
    TooShort,
    TooLong,
    NotUnique,
    NotMatchPattern,
    Forbidden,
    Correct;

    public int getId() {
        return ordinal();
    }
}
