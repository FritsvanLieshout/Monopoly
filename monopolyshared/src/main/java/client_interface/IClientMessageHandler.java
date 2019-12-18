package client_interface;

public interface IClientMessageHandler {
    void handleMessage(String message, String sessionId);
}
