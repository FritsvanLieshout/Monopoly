package messaging;

import client_interface.IGameClient;
import messages.NotEnoughMoneyMessage;

public class NotEnoughMoneyMessageHandler extends BaseClientMessageHandler<NotEnoughMoneyMessage> {

    private IGameClient gc;

    public NotEnoughMoneyMessageHandler(IGameClient client) { this.gc = client; }

    @Override
    public void handleMessageInternal(NotEnoughMoneyMessage message, String messageId) {
        gc.handleNotEnoughMoneyResponse();
    }
}
