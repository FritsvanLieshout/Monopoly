package models;

public class User {

    private int userId;
    private String username;
    private String password;
    private int place;
    private Wallet wallet;

    public User() {
        this.wallet = new Wallet(3500);
    }

    public User(int userId, String username) {
        this.place = 0;
        this.userId = userId;
        this.username = username;
        this.wallet = new Wallet(3500);
    }

    public int getUserId() {
        return userId;
    }

    public String getUsername() { return username; }

    public int getCurrentPlace() {
        return place;
    }

    public void setPlace(int place) {
        this.place = place;
    }

    public Wallet getWallet() {
        return wallet;
    }
}
