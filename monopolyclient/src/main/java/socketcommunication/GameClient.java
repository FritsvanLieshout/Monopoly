package socketcommunication;

import client_interface.IClientGUI;
import client_interface.IClientMessageGenerator;
import client_interface.IGameClient;
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
    public void handleLoginResponse(int userId) { clientGUI.processLoginResponse(userId); }

    @Override
    public void moveUser(int dice) { messageGenerator.moveUser(dice); }

    @Override
    public void handleMoveUserResponse(int dice, String sessionId) { clientGUI.processMoveUserResponse(dice, sessionId); }

    @Override
    public void handleUserListResponse(List<User> users) { clientGUI.processUserListResponse(users); }

    @Override
    public void handleStartGameResponse() { clientGUI.processStartGameResponse(); }

    @Override
    public void handleUpdateCurrentUser(User user, String sessionId) { clientGUI.processUpdateUser(user, sessionId); }

    @Override
    public void buyFootballPlayer() { messageGenerator.buyFootballPlayer(); }

    @Override
    public void handleUpdateBoard(String sessionId) { clientGUI.processUpdateBoardResponse(sessionId); }

    @Override
    public void handleNonValueSquareResponse() { clientGUI.processNonValueSquareResponse(); }

    @Override
    public void handleUserIsOverStart() { clientGUI.processUserIsOverStartResponse(); }

    @Override
    public void handlePayRentResponse(User currentUser, User ownedUser) { clientGUI.processPayRentResponse(currentUser, ownedUser); }

    @Override
    public void handleUserHasARedCardResponse(User currentUser) { clientGUI.processUserHasARedCardResponse(currentUser); }

    @Override
    public void handleUserIsInDressingRoomResponse(User currentUser) { clientGUI.processUserIsInDressingRoomResponse(currentUser); }

    @Override
    public void endTurn(int playerTurn) { messageGenerator.endTurn(playerTurn); }

    @Override
    public void handleSwitchTurnResponse(int playerTurn, String sessionId) { clientGUI.processSwitchTurnResponse(playerTurn, sessionId); }

    @Override
    public void handleNotEnoughMoneyResponse() { clientGUI.processNotEnoughMoneyResponse(); }

    @Override
    public void handlePropertyAlreadyOwnedResponse(int owner) { clientGUI.processAlreadyOwnedResponse(owner); }

    @Override
    public void handleCardMessageResponse(User user, String message) { clientGUI.processCardMessageResponse(user, message); }

    @Override
    public void handleUserIsBrokeResponse(User user) { clientGUI.processUserIsBrokeResponse(user); }
}
