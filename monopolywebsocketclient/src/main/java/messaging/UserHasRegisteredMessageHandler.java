package messaging;

import client_interface.IGameClient;
import messages.UserHasRegisteredMessage;

public class UserHasRegisteredMessageHandler extends BaseClientMessageHandler<UserHasRegisteredMessage> {

    private IGameClient gc;

    public UserHasRegisteredMessageHandler(IGameClient client) { this.gc = client; }

    @Override
    public void handleMessageInternal(UserHasRegisteredMessage message, String messageId) {
        gc.handleUserRegistered(message.getUsername());
    }
}
