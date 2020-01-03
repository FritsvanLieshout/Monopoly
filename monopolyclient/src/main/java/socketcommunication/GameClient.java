package socketcommunication;

import client_interface.IClientGUI;
import client_interface.IClientMessageGenerator;
import client_interface.IGameClient;
import models.Board;
import models.Square;
import models.User;

import java.util.List;

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
    public void moveUser(int dice) { messageGenerator.moveUser(dice); }

    @Override
    public void handleMoveUserResponse(int dice, String sessionId) { clientGUI.processMoveUserResponse(dice, sessionId); }

    @Override
    public void handleUsersInGameResponse(List<String> usernameList) { clientGUI.processUsersInGameResponse(usernameList); }
}
