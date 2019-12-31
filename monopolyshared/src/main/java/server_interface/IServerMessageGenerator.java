package server_interface;

public interface IServerMessageGenerator {
    void notifyUserAdded(String sessionId, String playerName);

    void notifyRegisterResult(String sessionId, boolean success);

}
