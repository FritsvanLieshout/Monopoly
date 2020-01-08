package messaging;

import client_interface.IGameClient;
import messages.PayRentMessage;

public class PayRentMessageHandler extends BaseClientMessageHandler<PayRentMessage> {

    private IGameClient gc;

    public PayRentMessageHandler(IGameClient client) { this.gc = client; }

    @Override
    public void handleMessageInternal(PayRentMessage message, String messageId) {
        gc.handlePayRentResponse(message.getUser());
    }
}
