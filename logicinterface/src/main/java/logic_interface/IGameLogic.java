package logic_interface;

import models.Dice;

public interface IGameLogic {
    int getDice(Dice dice);
    void moveUser(int playerNr, int dice1, int dice2);
}
