package game;

import models.Board;
import models.User;

public interface IMonopolyGame {
    /**
     * Move the user with the given dice.
     * The sum goes like this -> dice1 + dice2
     * With this result of the sum, so many steps goes te player forward on the board.
     * @param playerNr      identification of player for how many steps to go forward.
     * @param dice1         number of dice 1.
     * @param dice2         number of dice 2.
     * @throws IllegalArgumentException when:
     * User wants to throw twice.
     */
    public void moveUser(int playerNr, int dice1, int dice2);
}
