package messaging;

public interface IServerMessageHandler {
    void handleMessage(String message, String sessionId);
}
