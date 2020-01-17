package messaging;

import logic_interface.IGameLogic;
import messages.RegisterUserMessage;

public class RegisterUserMessageHandler extends BaseServerMessageHandler<RegisterUserMessage> {

    private IGameLogic game;

    public RegisterUserMessageHandler(IGameLogic game) { this.game = game; }

    @Override
    public void handleMessageInternal(RegisterUserMessage message, String sessionId) {
        game.register(message.getUsername(), message.getPassword(), sessionId);
    }
}
