package logic_interface;

import models.Dice;
import models.Square;
import models.User;

public interface IBoardLogic {
    Square moveUser(User user, int dice);
}
