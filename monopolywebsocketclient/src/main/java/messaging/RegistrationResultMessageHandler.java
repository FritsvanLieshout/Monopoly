package messaging;

import client_interface.IGameClient;
import messages.RegistrationResultMessage;

public class RegistrationResultMessageHandler extends BaseClientMessageHandler<RegistrationResultMessage> {

    private IGameClient gc;

    public RegistrationResultMessageHandler(IGameClient client) { this.gc = client; }

    @Override
    public void handleMessageInternal(RegistrationResultMessage message, String messageId) {
        gc.handleUserRegistrationResponse(message.isResult());
    }
}
