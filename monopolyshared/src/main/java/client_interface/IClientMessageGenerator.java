package client_interface;

import java.util.List;

public interface IClientMessageGenerator {
    void registerUserOnServer(String username, String password);
    void login(String username, String password);
    void moveUser(int dice);
}
