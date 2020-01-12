package messaging;

import client_interface.IGameClient;
import messages.PropertyAlreadyOwnedMessage;

public class PropertyAlreadyOwnedMessageHandler extends BaseClientMessageHandler<PropertyAlreadyOwnedMessage> {

    private IGameClient gc;

    public PropertyAlreadyOwnedMessageHandler(IGameClient client) { this.gc = client; }

    @Override
    public void handleMessageInternal(PropertyAlreadyOwnedMessage message, String messageId) {
        gc.handlePropertyAlreadyOwnedResponse(message.getOwnerId());
    }
}
