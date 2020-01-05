package messaging;

import client_interface.IClientHandlerFactory;
import client_interface.IClientMessageHandler;
import client_interface.IGameClient;
import messages.RegisterUserMessage;
import messages.RegistrationResultMessage;
import messages.UpdatePlaceOfCurrentUserMessage;

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
            case "UserListResultMessage":
                return new UserListResultMessageHandler(gc);
            case "StartGameMessage":
                return new StartGameMessageHandler(gc);
            case "UpdatePlaceOfCurrentUserMessage":
                return new UpdatePlaceOfCurrentUserMessageHandler(gc);
            default:
                return null;
        }
    }
}
