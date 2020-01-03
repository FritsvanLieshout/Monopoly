package messaging;

import client_interface.IGameClient;
import messages.UsersInGameResultMessage;

public class UsersInGameResultMessageHandler extends BaseClientMessageHandler<UsersInGameResultMessage> {

    private IGameClient gc;

    public UsersInGameResultMessageHandler(IGameClient client) { this.gc = client; }

    @Override
    public void handleMessageInternal(UsersInGameResultMessage message, String messageId) {
        gc.handleUsersInGameResponse(message.getUsernameList());
    }
}
