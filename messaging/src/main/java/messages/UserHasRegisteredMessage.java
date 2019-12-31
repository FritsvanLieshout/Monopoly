package messages;

public class UserHasRegisteredMessage {

    private String username;

    public UserHasRegisteredMessage(String username) { this.username = username; }

    public String getUsername() {
        return username;
    }
}
