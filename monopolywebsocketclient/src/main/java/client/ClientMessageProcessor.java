package client;

import messaging.ClientHandlerFactory;
import messaging.IClientMessageHandler;

public class ClientMessageProcessor {
    public void processMessage(String sessionId, String type, String data) {
        String simpleType = type.split("\\.")[type.split("\\.").length - 1];

        ClientHandlerFactory factory = new ClientHandlerFactory();
        IClientMessageHandler handler = factory.getHandler(simpleType);
        handler.handleMessage(data, sessionId);
    }
}
