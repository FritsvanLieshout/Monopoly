package logic_factory;

import logic.BoardLogic;
import logic.GameLogic;
import logic.UserLogic;
import logic_interface.IBoardLogic;
import logic_interface.IGameLogic;
import logic_interface.IUserLogic;
import server.ServerMessageGenerator;
import server_interface.IServerMessageGenerator;

public class LogicFactory {

    public IGameLogic getIGameLogic() {
        IGameLogic iGameLogic = new GameLogic();
        return iGameLogic;
    }

    public IUserLogic getIUserLogic() {
        IUserLogic iUserLogic = new UserLogic();
        return iUserLogic;
    }

    public IBoardLogic getIBoardLogic() {
        IBoardLogic iBoardLogic = new BoardLogic();
        return iBoardLogic;
    }
}
