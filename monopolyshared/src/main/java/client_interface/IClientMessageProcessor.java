package client_interface;

public interface IClientMessageProcessor {
    void processMessage(String sessionId, String type, String data);
}
