package logic_interface;

import models.Dice;
import models.Square;
import models.User;

/**
 * Interface provided by the GameLogic class
 * @author frits
 */
public interface IBoardLogic {
    /**
     * The user goes the steps forward that are given by the dice.
     * In this method is an int newPlace. newPlace is called by this method
     * board.getPositionOnBoard(user.getCurrentPlace().
     * If checkIfUserIsInDressingRoom is false the newPlace is the same method
     * as above. But this time is the number of the dice added ofter this method.
     * Place of the user set by newPlace.
     * @param user
     * @param dice
     * @return the square where the user became through newPlace.
     */
    Square moveUser(User user, int dice);
}
