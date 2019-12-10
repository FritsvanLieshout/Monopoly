package models;

public class Log {
    public static void print(User user, String message) {
        System.out.println(message);
        System.out.println("Username: " + user.getUsername() + ", Money: " + user.getWallet().getMoney());
    }
}
