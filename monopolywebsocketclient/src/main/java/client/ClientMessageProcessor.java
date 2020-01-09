package client;

import client_interface.IClientHandlerFactory;
import client_interface.IClientMessageProcessor;
import client_interface.IGameClient;
import client_interface.IClientMessageHandler;

public class ClientMessageProcessor implements IClientMessageProcessor {

    private IGameClient gc;
    private IClientHandlerFactory factory;

    public ClientMessageProcessor(IClientHandlerFactory factory) { this.factory = factory; }

    public void registerGameClient(IGameClient gameClient) { this.gc = gameClient; }

    public void processMessage(String sessionId, String type, String data) {
        String classname = type.split("\\.")[type.split("\\.").length - 1];

        IClientMessageHandler handler = factory.getHandler(classname, gc);
        handler.handleMessage(data, sessionId);
    }
}
