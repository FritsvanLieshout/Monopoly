package messaging;

import messages.UserTestResultMessage;

public class TestResultHandler extends BaseClientMessageHandler<UserTestResultMessage> {

    //private GameClient gameClient;

    @Override
    public void handleMessageInternal(UserTestResultMessage message, String messageId) {
        //GameClient

    }
}
