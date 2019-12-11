package messages;

public class UserTestMessage {
    private String username;
    private String password;

    public UserTestMessage(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
