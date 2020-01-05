package messaging;

import client_interface.IGameClient;
import messages.UpdatePlaceOfCurrentUserMessage;

public class UpdatePlaceOfCurrentUserMessageHandler extends BaseClientMessageHandler<UpdatePlaceOfCurrentUserMessage> {

    private IGameClient gc;

    public UpdatePlaceOfCurrentUserMessageHandler(IGameClient client) { this.gc = client; }

    @Override
    public void handleMessageInternal(UpdatePlaceOfCurrentUserMessage message, String messageId) {
        gc.handleUpdatePlaceOfCurrentUserResponse(message.getCurrentPlace(), message.getSessionId());
    }
}
