package messages;

public class RegisterUserMessage {

    private String username;
    private String password;

    public RegisterUserMessage(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() { return username; }

    public String getPassword() { return password; }
}
