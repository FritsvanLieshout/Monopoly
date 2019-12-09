package logic_interface;

import models.Dice;
import models.User;

public interface IGameLogic {
    void startBonus(int playerNr);
    void redCard(int playerNr);
}
