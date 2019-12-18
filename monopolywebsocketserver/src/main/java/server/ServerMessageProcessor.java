package server;

import server_interface.IServerMessageHandler;
import server_interface.IServerMessageProcessor;
import messaging.ServerHandlerFactory;

public class ServerMessageProcessor implements IServerMessageProcessor {
    public void processMessage(String sessionId, String type, String data) {
        String simpleType = type.split(".")[type.split(".").length - 1];

        ServerHandlerFactory factory = new ServerHandlerFactory();
        IServerMessageHandler handler = factory.getHandler(simpleType);
        handler.handleMessage(data, sessionId);
    }
}
