package messaging;

import logic_interface.IGameLogic;
import messages.MoveUserMessage;

public class MoveUserMessageHandler extends BaseServerMessageHandler<MoveUserMessage> {

    private IGameLogic game;

    public MoveUserMessageHandler(IGameLogic game) { this.game = game; }

    @Override
    public void handleMessageInternal(MoveUserMessage message, String sessionId) {
        game.moveUser(message.getDice(), sessionId);
    }
}
