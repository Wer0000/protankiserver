package ua.lann.protankiserver.enums;

public enum CaptchaLocation {
    LoginForm(0),
    RegisterForm(1),
    ClientStartup(2),
    RestorePasswordForm(3),
    EmailChangeHash(4),
    AccountSettingsForm(5);

    public final int id;
    CaptchaLocation(int id) {
        this.id = id;
    }
}
