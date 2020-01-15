package messaging;

import client_interface.IClientHandlerFactory;
import client_interface.IClientMessageHandler;
import client_interface.IGameClient;

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
            case "UpdateCurrentUserMessage":
                return new UpdateCurrentUserMessageHandler(gc);
            case "UpdateBoardMessage":
                return new UpdateBoardMessageHandler(gc);
            case "NonValueSquareMessage":
                return new NonValueSquareMessageHandler(gc);
            case "UserIsOverStartMessage":
                return new UserIsOverStartMessageHandler(gc);
            case "PayRentMessage":
                return new PayRentMessageHandler(gc);
            case "UserHasARedCardMessage":
                return new UserHasARedCardMessageHandler(gc);
            case "UserIsInDressingRoomMessage":
                return new UserIsInDressingRoomMessageHandler(gc);
            case "SwitchTurnResultMessage":
                return new SwitchTurnResultMessageHandler(gc);
            case "NotEnoughMoneyMessage":
                return new NotEnoughMoneyMessageHandler(gc);
            case "PropertyAlreadyOwnedMessage":
                return new PropertyAlreadyOwnedMessageHandler(gc);
            case "CardMessage":
                return new CardMessageHandler(gc);
            case "BrokeMessage":
                return new BrokeMessageHandler(gc);
            case "SquareMessage":
                return new SquareMessageHandler(gc);
            default:
                return null;
        }
    }
}
