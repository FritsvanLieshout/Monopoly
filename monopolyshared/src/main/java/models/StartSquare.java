package models;

public class StartSquare extends Square {

    public StartSquare(String name) {
        super(name);
    }

    @Override
    public void action(User user, Board board) {
        user.getWallet().addMoneyToWallet(1000);
    }
}
