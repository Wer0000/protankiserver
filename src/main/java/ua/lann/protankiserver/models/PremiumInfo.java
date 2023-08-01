package ua.lann.protankiserver.models;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PremiumInfo {
    private final boolean needShowNotificationCompletionPremium;
    private final boolean needShowWelcomeAlert;
    private final float reminderCompletionPremiumTime;
    private final boolean wasShowAlertForFirstPurchasePremium;
    private final boolean wasShowReminderCompletionPremium;
    private final int lifetimeInSeconds;
}
