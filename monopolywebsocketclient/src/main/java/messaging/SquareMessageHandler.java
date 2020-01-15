package messaging;

import client_interface.IGameClient;
import messages.SquareMessage;

public class SquareMessageHandler extends BaseClientMessageHandler<SquareMessage> {

    private IGameClient gc;

    public SquareMessageHandler(IGameClient client) { this.gc = client; }

    @Override
    public void handleMessageInternal(SquareMessage message, String messageId) {
        gc.handleSquareMessageResponse(message.getUser(), message.getMessage());
    }
}
