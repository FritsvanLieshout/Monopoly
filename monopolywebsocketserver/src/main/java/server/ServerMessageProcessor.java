package server;

import messaging.IServerMessageHandler;
import messaging.ServerHandlerFactory;

public class ServerMessageProcessor {
    public void processMessage(String sessionId, String type, String data) {
        String simpleType = type.split(".")[type.split(".").length - 1];

        ServerHandlerFactory factory = new ServerHandlerFactory();
        IServerMessageHandler handler = factory.getHandler(simpleType);
        handler.handleMessage(data, sessionId);
    }
}
