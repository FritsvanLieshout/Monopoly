package messaging;

import logic_interface.IGameLogic;
import messages.UsersInGameMessage;

public class UsersInGameMessageHandler extends BaseServerMessageHandler<UsersInGameMessage> {

    private IGameLogic game;

    public UsersInGameMessageHandler(IGameLogic game) { this.game = game; }

    @Override
    public void handleMessageInternal(UsersInGameMessage message, String sessionId) {

    }
}
