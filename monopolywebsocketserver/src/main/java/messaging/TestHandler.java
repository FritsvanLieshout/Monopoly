package messaging;

import messages.UserTestMessage;

public class TestHandler extends BaseServerMessageHandler<UserTestMessage> {
    //iGameLogic
    @Override
    public void handleMessageInternal(UserTestMessage message, String sessionId) {
        //iGameLogic.Login();
    }
}
