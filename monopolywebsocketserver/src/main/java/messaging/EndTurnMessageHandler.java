package messaging;

import logic_interface.IGameLogic;
import messages.EndTurnMessage;

public class EndTurnMessageHandler extends BaseServerMessageHandler<EndTurnMessage> {

    private IGameLogic game;

    public EndTurnMessageHandler(IGameLogic game) { this.game = game; }

    @Override
    public void handleMessageInternal(EndTurnMessage message, String sessionId) {
        game.switchTurn(message.getPlayerTurn());
    }
}
