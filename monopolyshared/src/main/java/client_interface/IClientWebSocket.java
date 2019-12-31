package client_interface;

public interface IClientWebSocket {
    void start();

    void stop();

    void setMessageProcessor(IClientMessageProcessor processor);

    void send(Object object);

    void onWebSocketMessageReceived(String message, String sessionId);
}
