package messaging;

import client_interface.IGameClient;
import messages.BrokeMessage;

public class BrokeMessageHandler extends BaseClientMessageHandler<BrokeMessage> {

    private IGameClient gc;

    public BrokeMessageHandler(IGameClient client) { this.gc = client; }

    @Override
    public void handleMessageInternal(BrokeMessage message, String messageId) {
        gc.handleUserIsBrokeResponse(message.getUser());
    }
}
