package messages;

public class UserTestResultMessage {
    private String username;

    public UserTestResultMessage(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }
}
