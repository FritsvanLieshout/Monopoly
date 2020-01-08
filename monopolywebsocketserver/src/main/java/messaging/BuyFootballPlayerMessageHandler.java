package messaging;

import logic_interface.IGameLogic;
import messages.BuyFootballPlayerMessage;

public class BuyFootballPlayerMessageHandler extends BaseServerMessageHandler<BuyFootballPlayerMessage> {

    private IGameLogic game;

    public BuyFootballPlayerMessageHandler(IGameLogic game) { this.game = game; }

    @Override
    public void handleMessageInternal(BuyFootballPlayerMessage message, String sessionId) {
        game.buyFootballPlayer(sessionId);
    }
}
