package messaging;

import logic_interface.IGameLogic;
import messages.LoginMessage;

public class LoginMessageHandler extends BaseServerMessageHandler<LoginMessage> {

    private IGameLogic game;

    public LoginMessageHandler(IGameLogic game) { this.game = game; }

    @Override
    public void handleMessageInternal(LoginMessage message, String sessionId) {
        game.login(message.getUsername(), message.getPassword(), sessionId);
    }
}
