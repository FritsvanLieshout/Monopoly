package messaging;

import client_interface.IGameClient;
import messages.MoveUserResultMessage;

public class MoveUserResultMessageHandler extends BaseClientMessageHandler<MoveUserResultMessage> {

    private IGameClient gc;

    public MoveUserResultMessageHandler(IGameClient client) { this.gc = client; }

    @Override
    public void handleMessageInternal(MoveUserResultMessage message, String messageId) {
        gc.handleMoveUserResponse(message.getDice(), message.getSessionId());
    }
}
