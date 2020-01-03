package socketcommunication;

import client_interface.IClientGUI;
import client_interface.IClientMessageGenerator;
import client_interface.IGameClient;
import models.User;

public class GameClient implements IGameClient {
    private IClientMessageGenerator messageGenerator;
    private IClientGUI clientGUI;

    public GameClient(IClientMessageGenerator generator) { this.messageGenerator = generator; }

    public void registerClientGUI(IClientGUI gui) { this.clientGUI = gui; }

    @Override
    public void registerUser(String username, String password) { messageGenerator.registerUserOnServer(username, password); }

    @Override
    public void loginUser(String username, String password) { messageGenerator.login(username, password); }

    @Override
    public void handleUserRegistrationResponse(boolean success) { clientGUI.processRegistrationResponse(success); }

    @Override
    public void handleUserRegistered(String username) { clientGUI.processUserRegistered(username);}

    @Override
    public void handleLoginResponse(String token) { clientGUI.processLoginResponse(token); }

    @Override
    public void moveUser(User user, int dice) { clientGUI.moveUser(); }
}
