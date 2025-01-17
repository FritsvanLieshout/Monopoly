package models;

public class User {

    private int userId;
    private String sessionId;
    private String username;
    private String password;
    private int place;
    private Wallet wallet;
    boolean isInDressingRoom;
    boolean isBroke;

    public User() {
        this.wallet = new Wallet(6000);
    }

    public User(int userId, String sessionId, String username, String password) {
        this.userId = userId;
        this.place = 0;
        this.sessionId = sessionId;
        this.username = username;
        this.password = password;
        this.wallet = new Wallet(6000);
        this.isInDressingRoom = false;
        this.isBroke = false;
    }

    public User(int userId, String sessionId, String username) {
        this.userId = userId;
        this.place = 0;
        this.sessionId = sessionId;
        this.username = username;
        this.wallet = new Wallet(6000);
        this.isInDressingRoom = false;
        this.isBroke = false;
    }

    public int getUserId() { return userId; }

    public void setUserId(int userId) { this.userId = userId; }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) { this.sessionId = sessionId; }

    public String getUsername() { return username; }

    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return password; }

    public void setPassword(String password) { this.password = password; }

    public int getCurrentPlace() { return place; }

    public void setPlace(int place) { this.place = place; }

    public Wallet getWallet() {
        return wallet;
    }

    public void setWallet(Wallet wallet) { this.wallet = wallet; }

    public boolean isInDressingRoom() { return this.isInDressingRoom; }

    public void setInDressingRoom(boolean isInDressingRoom) { this.isInDressingRoom = isInDressingRoom; }

    public boolean isBroke() { return isBroke; }

    public void setBroke(boolean isBroke) { this.isBroke = isBroke; }
}
