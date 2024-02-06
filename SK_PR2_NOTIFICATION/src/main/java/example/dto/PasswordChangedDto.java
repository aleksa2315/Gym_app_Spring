package example.dto;

public class PasswordChangedDto {
    private Long userId;
    private String notificationType;
    private String firstName;
    private String lastName;
    private String password;

    public PasswordChangedDto() {
    }

    public PasswordChangedDto(Long userId, String notificationType, String firstName, String lastName, String password) {
        this.userId = userId;
        this.notificationType = notificationType;
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
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

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
