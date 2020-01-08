package messaging;

import client_interface.IGameClient;
import messages.UpdateCurrentUserMessage;

public class UpdateCurrentUserMessageHandler extends BaseClientMessageHandler<UpdateCurrentUserMessage> {

    private IGameClient gc;

    public UpdateCurrentUserMessageHandler(IGameClient client) { this.gc = client; }

    @Override
    public void handleMessageInternal(UpdateCurrentUserMessage message, String messageId) {
        gc.handleUpdateCurrentUser(message.getUser(), message.getSessionId());
    }
}
