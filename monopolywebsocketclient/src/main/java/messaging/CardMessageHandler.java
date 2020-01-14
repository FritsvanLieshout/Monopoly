package messaging;

import client_interface.IGameClient;
import messages.CardMessage;

public class CardMessageHandler extends BaseClientMessageHandler<CardMessage> {

    private IGameClient gc;

    public CardMessageHandler(IGameClient client) { this.gc = client; }

    @Override
    public void handleMessageInternal(CardMessage message, String messageId) {
        gc.handleCardMessageResponse(message.getUser(), message.getMessage(), message.isResult());
    }
}
