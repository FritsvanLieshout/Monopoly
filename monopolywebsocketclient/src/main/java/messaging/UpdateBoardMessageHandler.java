package messaging;

import client_interface.IGameClient;
import messages.UpdateBoardMessage;

public class UpdateBoardMessageHandler extends BaseClientMessageHandler<UpdateBoardMessage> {

    private IGameClient gc;

    public UpdateBoardMessageHandler(IGameClient client) { this.gc = client; }

    @Override
    public void handleMessageInternal(UpdateBoardMessage message, String messageId) {
        gc.handleUpdateBoard(message.getSessionId());
    }
}
