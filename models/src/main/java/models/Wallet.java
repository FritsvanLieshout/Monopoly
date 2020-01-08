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

    public void setMoney(int money) { this.money = money; }

    public void addMoneyToWallet(int amount) {
        money += amount;
    }

    public void withDrawMoneyOfWallet(int amount) { money -= amount; }
}
