package server_interface;

public interface IServerMessageProcessor {
    void processMessage(String sessionId, String type, String data);
}
