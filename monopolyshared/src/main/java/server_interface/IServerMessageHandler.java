package server_interface;

public interface IServerMessageHandler {
    void handleMessage(String message, String sessionId);
}
