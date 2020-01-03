package messaging;

import logic_interface.IGameLogic;
import server_interface.IServerHandlerFactory;
import server_interface.IServerMessageHandler;

public class ServerHandlerFactory implements IServerHandlerFactory {

    public IServerMessageHandler getHandler(String classname, Object game) {

        IGameLogic iGame = (IGameLogic)game;

        switch (classname) {
            case "RegisterUserMessage":
                return new RegisterUserMessageHandler(iGame);
            case "LoginUserMessage":
                return new LoginMessageHandler(iGame);
            default:
                return null;
        }
    }
}
