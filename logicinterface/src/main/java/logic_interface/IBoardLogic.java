package logic_interface;

import models.Dice;
import models.Square;

public interface IBoardLogic {
    int getDice(Dice dice);
    Square moveUser(int playerNr, int dice);
}
