package models;

public class User {

    private int userId;
    private String username;
    private String password;
    private int place;
    private Wallet wallet;
    boolean isInDressingRoom;

    public User() {
        this.wallet = new Wallet(3500);
    }

    public User(int userId, String username) {
        this.place = 0;
        this.userId = userId;
        this.username = username;
        this.wallet = new Wallet(3500);
        this.isInDressingRoom = false;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) { this.userId = userId; }

    public String getUsername() { return username; }

    public void setUsername(String username) { this.username = username; }

    public void setPassword(String password) { this.password = password; }

    public int getCurrentPlace() { return place; }

    public void setPlace(int place) { this.place = place; }

    public Wallet getWallet() {
        return wallet;
    }

    public boolean isInDressingRoom() { return this.isInDressingRoom; }

    public void setInDressingRoom(boolean isInDressingRoom) { this.isInDressingRoom = isInDressingRoom; }
}
