package messages;

public class LoginResultMessage {

    private String token;

    public LoginResultMessage(String token) { this.token = token; }

    public String getToken() { return this.token; }

    public void setToken(String token) { this.token = token; }
}
