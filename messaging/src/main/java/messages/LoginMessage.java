package messages;

public class LoginMessage {

    private String username;
    private String password;
    private boolean singlePlayer;

    public LoginMessage(String username, String password, boolean singlePlayer) {
        this.username = username;
        this.password = password;
        this.singlePlayer = singlePlayer;
    }

    public String getUsername() { return username; }

    public String getPassword() { return password; }

    public boolean isSinglePlayer() { return singlePlayer; }
}
