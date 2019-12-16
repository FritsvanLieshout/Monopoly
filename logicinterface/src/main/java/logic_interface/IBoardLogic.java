package logic_interface;

import models.*;

/**
 * Interface provided by the GameLogic class
 * @author frits
 */
public interface IBoardLogic {
    /**
     * This method returns the squares of the board that have been initialized in the board logic.
     * @return squares
     */
    Square[] getSquares();

    /**
     * This method return the board that have been initialized in this class.
     * @return board
     */
    Board getBoard();
}
