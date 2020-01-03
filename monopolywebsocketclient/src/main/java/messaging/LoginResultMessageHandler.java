package messaging;

import client_interface.IGameClient;
import messages.LoginResultMessage;

public class LoginResultMessageHandler extends BaseClientMessageHandler<LoginResultMessage> {

    private IGameClient gc;

    public LoginResultMessageHandler(IGameClient client) { this.gc = client; }

    @Override
    public void handleMessageInternal(LoginResultMessage message, String sessionId) {
        gc.handleLoginResponse(message.getToken());
    }
}
