package messaging;

import client_interface.IClientHandlerFactory;
import client_interface.IClientMessageHandler;

public class ClientHandlerFactory implements IClientHandlerFactory {
    public IClientMessageHandler getHandler(String classname) {
        switch (classname) {
            case "USERTESTMESSAGE":
                return new TestResultHandler();
            default:
                return null;
        }
    }
}
