package models;

public class Log {
    public static void print(User user, String message) {
        System.out.println("Current player: " + user.getUsername() + " has " + user.getWallet().getMoney() + " in the wallet. Current place on board: " + user.getCurrentPlace() + ". " + message + ".");
    }
}
