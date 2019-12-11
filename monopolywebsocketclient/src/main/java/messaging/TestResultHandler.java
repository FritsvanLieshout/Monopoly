package messaging;

import messages.UserTestResultMessage;

public class TestResultHandler extends BaseClientMessageHandler<UserTestResultMessage> {
    @Override
    public void handleMessageInternal(UserTestResultMessage message, String messageId) {
        //GameClient
    }
}
