package messaging;

import client_interface.IClientHandlerFactory;
import client_interface.IClientMessageHandler;
import client_interface.IGameClient;
import messages.RegisterUserMessage;
import messages.RegistrationResultMessage;

public class ClientHandlerFactory implements IClientHandlerFactory {
    public IClientMessageHandler getHandler(String classname, Object game) {
        IGameClient gc = (IGameClient)game;

        switch (classname) {
            case "RegistrationResultMessage":
                return new RegistrationResultMessageHandler(gc);
            case "UserHasRegisteredMessage":
                return new UserHasRegisteredMessageHandler(gc);
            case "LoginResultMessage":
                return new LoginResultMessageHandler(gc);
            case "MoveUserResultMessage":
                return new MoveUserResultMessageHandler(gc);
            case "UsersInGameResultMessage":
                return new UsersInGameResultMessageHandler(gc);
            default:
                return null;
        }
    }
}
