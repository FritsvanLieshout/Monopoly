package models;

public class RedCardSquare extends Square {
    public RedCardSquare(String name) {
        super(name);
    }

    @Override
    public void action(User user, Board board) {
        //Log dat de user naar de gevangenis moet.
        board.moveUser(user, -board.getTotalSquares() / 2);
    }
}
