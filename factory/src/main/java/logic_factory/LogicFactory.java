package logic_factory;

import logic.GameLogic;
import logic.UserLogic;
import logic_interface.IGameLogic;
import logic_interface.IUserLogic;

public class LogicFactory {
    public IGameLogic getIGameLogic() {
        IGameLogic iGameLogic = new GameLogic();
        return iGameLogic;
    }

    public IUserLogic getIUserLogic() {
        IUserLogic iUserLogic = new UserLogic();
        return iUserLogic;
    }
}
