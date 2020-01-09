package logic_interface;

import models.Board;
import models.Square;

import java.util.ArrayList;

/**
 * Interface provided by the GameLogic class
 * @author frits
 */
public interface IBoardLogic {
    /**
     * This method return the board that have been initialized in this class.
     * @return board
     */
    Board getBoard();

    /**
     * This method return a list of al the squares on the board.
     * @return squares -> List
     */
    ArrayList<Square> getSquareList();
}
