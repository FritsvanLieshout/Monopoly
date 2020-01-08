package messaging;

import client_interface.IGameClient;
import messages.NonValueSquareMessage;

public class NonValueSquareMessageHandler extends BaseClientMessageHandler<NonValueSquareMessage> {

    private IGameClient gc;

    public NonValueSquareMessageHandler(IGameClient client) { this.gc = client; }

    @Override
    public void handleMessageInternal(NonValueSquareMessage message, String messageId) {
        gc.handleNonValueSquareResponse();
    }
}
