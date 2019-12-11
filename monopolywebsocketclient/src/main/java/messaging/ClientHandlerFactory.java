package messaging;

import messages.UserTestMessage;

public class ClientHandlerFactory {
    public IClientMessageHandler getHandler(String classname) {
        switch (classname) {
            case "USERTESTMESSAGE":
                return new TestResultHandler();
            default:
                return null;
        }
    }
}
