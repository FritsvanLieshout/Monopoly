package messaging;

import client_interface.IGameClient;
import messages.UserIsInDressingRoomMessage;

public class UserIsInDressingRoomMessageHandler extends BaseClientMessageHandler<UserIsInDressingRoomMessage> {

    private IGameClient gc;

    public UserIsInDressingRoomMessageHandler(IGameClient client) { this.gc = client; }

    @Override
    public void handleMessageInternal(UserIsInDressingRoomMessage message, String messageId) {
        gc.handleUserIsInDressingRoomResponse(message.getUser());
    }
}
