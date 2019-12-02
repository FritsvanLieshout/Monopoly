package models;

public class Wallet {

    private int money;

    public Wallet() {
        this(0);
    }

    public Wallet(int money) {
        this.money = money;
    }

    public int getMoney() {
        return money;
    }

    public void addMoneyToWallet(int amount) {
        money += amount;
    }

    public void subtractMoney(int amount) {
        money -= amount;
    }
}
