package client_interface;

public interface IClientMessageGenerator {
    void registerUserOnServer(String username, String password);
    void login(String username, String password);
}
