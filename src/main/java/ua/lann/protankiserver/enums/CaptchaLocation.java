package ua.lann.protankiserver.enums;

public enum CaptchaLocation {
    LoginForm,
    RegisterForm,
    ClientStartup,
    RestorePasswordForm,
    EmailChangeHash,
    AccountSettingsForm;

    public int getId() { return ordinal(); }
}
