package messages;

public class LoginResultMessage {

    private int userId;

    public LoginResultMessage(int userId) { this.userId = userId; }

    public int getUserId() { return userId; }
}
