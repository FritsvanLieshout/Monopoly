package models;

public class FootballPlayerSquare extends Square {

    private int price;
    private int rentPrice;
    private int owner = -1;

    public FootballPlayerSquare() { }

    public FootballPlayerSquare(String name, int price) {
        super(name);
        this.price = price;
        this.rentPrice = price / 50 * 10;
    }

    public int getPrice() {
        return price;
    }

    public int getRentPrice() {
        return rentPrice;
    }

    public void setOwner(int owner) {
        this.owner = owner;
    }

    //This methods called in GameLogic
    public void buySquare(User user) {
        int price = getPrice();
        user.getWallet().withDrawMoneyOfWallet(price);
        setOwner(user.getUserId());
        //Een log dat de user de speler wilt kopen.
    }

    public void payRent(User user, Board board) {
        int rent = getRentPrice();
        user.getWallet().withDrawMoneyOfWallet(rent);
        board.getUser(owner).getWallet().addMoneyToWallet(rent);
        //Een log dat user x aantal geld huur heeft betaal aan de eigenaar.
    }

    public boolean wantToBuySquare(User user) {
        //Van frontend naar backend. User moet op de knop gedrukt hebben.
        //if (...) { return true; }
        //else {
        return false;
        //}
    }
}
