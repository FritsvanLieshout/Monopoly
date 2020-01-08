package client_interface;

import models.Board;

public interface IClientMessageGenerator {
    void registerUserOnServer(String username, String password);
    void login(String username, String password);
    void moveUser(int dice);
    void buyFootballPlayer();
}
