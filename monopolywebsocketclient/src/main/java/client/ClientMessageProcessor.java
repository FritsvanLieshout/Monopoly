package client;

import client_interface.IClientMessageProcessor;
import messaging.ClientHandlerFactory;
import client_interface.IClientMessageHandler;

public class ClientMessageProcessor implements IClientMessageProcessor {
    public void processMessage(String sessionId, String type, String data) {
        String simpleType = type.split("\\.")[type.split("\\.").length - 1];

        ClientHandlerFactory factory = new ClientHandlerFactory();
        IClientMessageHandler handler = factory.getHandler(simpleType);
        handler.handleMessage(data, sessionId);
    }
}
