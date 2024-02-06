package example.dto;

public class ReminderNotificationDto {
    private Long userId;
    private String notificationType;

    public ReminderNotificationDto() {
    }

    public ReminderNotificationDto(Long userId, String notificationType) {
        this.userId = userId;
        this.notificationType = notificationType;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getNotificationType() {
        return notificationType;
    }

    public void setNotificationType(String notificationType) {
        this.notificationType = notificationType;
    }
}
