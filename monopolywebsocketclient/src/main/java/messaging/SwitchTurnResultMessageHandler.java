package messaging;

import client_interface.IGameClient;
import messages.SwitchTurnResultMessage;

public class SwitchTurnResultMessageHandler extends BaseClientMessageHandler<SwitchTurnResultMessage> {

    private IGameClient gc;

    public SwitchTurnResultMessageHandler(IGameClient client) { this.gc = client; }

    @Override
    public void handleMessageInternal(SwitchTurnResultMessage message, String messageId) {
        gc.handleSwitchTurnResponse(message.getPlayerTurn());
    }
}
