package logic;

import logic_interface.IBoardLogic;
import models.*;

public class BoardLogic implements IBoardLogic {

    Board board;

    public BoardLogic() {
        board = new Board();
    }

    @Override
    public int getDice(Dice dice) {
        return dice.getNofDice();
    }

    User user = new User(1, "Jan");

    @Override
    public Square moveUser(int playerNr, int dice) {
        int newPlace = board.getPositionOnBoard(user.getCurrentPlace() + dice);

        if (playerNr == user.getUserId()) {
            user.setPlace(newPlace);
            board.getSquares()[newPlace].doAction(user, board);

            Log.print(user.getUsername() + " goes to " + board.getSquares()[user.getCurrentPlace()].getSquareName()); //squares[user.getCurrentPlace()].getName();
        }
        return board.getSquares()[newPlace];
    }
}
