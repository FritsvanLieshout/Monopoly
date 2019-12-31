package client_interface;

public interface IClientMessageProcessor {
    void registerGameClient(IGameClient gameClient);
    void processMessage(String sessionId, String type, String data);
}
