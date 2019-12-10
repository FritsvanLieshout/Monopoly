package logic_interface;

import models.Dice;
import models.User;

public interface IGameLogic {
    int getDice(Dice dice);
    void startBonus(User user);
    void redCard(User user);
    boolean checkIfUserWantToBuyPlayer(User user);
}
