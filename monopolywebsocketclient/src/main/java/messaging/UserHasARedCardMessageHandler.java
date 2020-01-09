package messaging;

import client_interface.IGameClient;
import messages.UserHasARedCardMessage;

public class UserHasARedCardMessageHandler extends BaseClientMessageHandler<UserHasARedCardMessage> {

    private IGameClient gc;

    public UserHasARedCardMessageHandler(IGameClient client) { this.gc = client; }

    @Override
    public void handleMessageInternal(UserHasARedCardMessage message, String messageId) {
        gc.handleUserHasARedCardResponse(message.getUser());
    }
}
