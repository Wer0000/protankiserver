package ua.lann.protankiserver.enums;

public enum ValidationResult {
    TooShort(0),
    TooLong(1),
    NotUnique(2),
    NotMatchPattern(3),
    Forbidden(4),
    Correct(5);

    public final int id;
    ValidationResult(int id) {
        this.id = id;
    }
}
