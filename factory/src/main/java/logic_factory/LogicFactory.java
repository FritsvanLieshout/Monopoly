package logic_factory;

import logic.BoardLogic;
import logic.GameLogic;
import logic_interface.IBoardLogic;
import logic_interface.IGameLogic;

public class LogicFactory {

    public IGameLogic getIGameLogic() {
        IGameLogic iGameLogic = new GameLogic();
        return iGameLogic;
    }

    public IBoardLogic getIBoardLogic() {
        IBoardLogic iBoardLogic = new BoardLogic();
        return iBoardLogic;
    }
}
