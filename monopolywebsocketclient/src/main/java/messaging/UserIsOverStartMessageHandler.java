package messaging;

import client_interface.IGameClient;
import messages.UserIsOverStartMessage;

public class UserIsOverStartMessageHandler extends BaseClientMessageHandler<UserIsOverStartMessage> {

    private IGameClient gc;

    public UserIsOverStartMessageHandler(IGameClient client) { this.gc = client; }

    @Override
    public void handleMessageInternal(UserIsOverStartMessage message, String messageId) {
        gc.handleUserIsOverStart();
    }
}
