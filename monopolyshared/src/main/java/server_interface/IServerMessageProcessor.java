package server_interface;

import logic_interface.IGameLogic;

public interface IServerMessageProcessor {

    void registerGame(IGameLogic gameLogic);
    void processMessage(String sessionId, String type, String data);
    void handleDisconnect(String sessionId);
}
