package messaging;

import client_interface.IGameClient;
import messages.StartGameMessage;

public class StartGameMessageHandler extends BaseClientMessageHandler<StartGameMessage> {

    private IGameClient gc;

    public StartGameMessageHandler(IGameClient client) { this.gc = client; }

    @Override
    public void handleMessageInternal(StartGameMessage message, String messageId) {
        gc.handleStartGameResponse();
    }
}
