package server;

import logic_interface.IGameLogic;
import server_interface.IServerHandlerFactory;
import server_interface.IServerMessageHandler;
import server_interface.IServerMessageProcessor;

public class ServerMessageProcessor implements IServerMessageProcessor {

    private IGameLogic game;
    private IServerHandlerFactory factory;

    public ServerMessageProcessor(IServerHandlerFactory factory) { this.factory = factory; }

    public void registerGame(IGameLogic game) { this.game = game; }

    public IGameLogic getGame() { return game; }

    @Override
    public void processMessage(String sessionId, String type, String data) {
        String classname = type.split("\\.")[type.split("\\.").length - 1];
        IServerMessageHandler handler = factory.getHandler(classname, getGame());
        handler.handleMessage(data, sessionId);
    }

    @Override
    public void handleDisconnect(String sessionId) {
        getGame().processClientDisconnect(sessionId);
    }
}
