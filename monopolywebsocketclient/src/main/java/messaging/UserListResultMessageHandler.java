package messaging;

import client_interface.IGameClient;
import messages.UserListResultMessage;

public class UserListResultMessageHandler extends BaseClientMessageHandler<UserListResultMessage> {

    private IGameClient gc;

    public UserListResultMessageHandler(IGameClient client) { this.gc = client; }

    @Override
    public void handleMessageInternal(UserListResultMessage message, String messageId) {
        gc.handleUserListResponse(message.getUsers());
    }
}
