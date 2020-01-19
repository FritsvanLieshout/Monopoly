package server;

import messages.*;
import models.User;
import server_interface.IServerMessageGenerator;
import server_interface.IServerWebSocket;
import java.util.List;

public class ServerMessageGenerator implements IServerMessageGenerator {

    private IServerWebSocket serverWebSocket;

    public ServerMessageGenerator(IServerWebSocket serverWebSocket) { this.serverWebSocket = serverWebSocket; }

    @Override
    public void notifyUserAdded(String sessionId, String username) {
        UserHasRegisteredMessage msg =  new UserHasRegisteredMessage(username);
        serverWebSocket.sendToOthers(sessionId, msg);
    }

    @Override
    public void notifyRegisterResult(String sessionId, boolean success) {
        RegistrationResultMessage msg = new RegistrationResultMessage(success);
        serverWebSocket.sendTo(sessionId, msg);
    }

    @Override
    public void notifyLoginResult(String sessionId, int userId) {
        LoginResultMessage msg = new LoginResultMessage(userId);
        serverWebSocket.sendTo(sessionId, msg);
    }

    @Override
    public void notifyMoveUserMessage(int dice, String sessionId) {
        MoveUserResultMessage msg = new MoveUserResultMessage(dice, sessionId);
        serverWebSocket.broadcast(msg);
    }

    @Override
    public void updateUserList(List<User> onlineUsers, String sessionId) {
        UserListResultMessage msg = new UserListResultMessage(onlineUsers);
        serverWebSocket.sendTo(sessionId, msg);
    }

    @Override
    public void notifyStartGame() {
        StartGameMessage msg = new StartGameMessage();
        serverWebSocket.broadcast(msg);
    }

    @Override
    public void updateCurrentUser(User user, String sessionId) {
        UpdateCurrentUserMessage msg = new UpdateCurrentUserMessage(user, sessionId);
        serverWebSocket.broadcast(msg);
    }

    @Override
    public void updateBoard(String sessionId) {
        UpdateBoardMessage msg = new UpdateBoardMessage(sessionId);
        serverWebSocket.broadcast(msg);
    }

    @Override
    public void notifyNonValueSquare(String sessionId) {
        NonValueSquareMessage msg = new NonValueSquareMessage();
        serverWebSocket.sendTo(sessionId, msg);
    }

    @Override
    public void notifyUserOverStart(String sessionId) {
        UserIsOverStartMessage msg = new UserIsOverStartMessage();
        serverWebSocket.sendTo(sessionId, msg);
    }

    @Override
    public void notifyPayRent(User currentUser, User ownedUser) {
        PayRentMessage msg = new PayRentMessage(currentUser, ownedUser);
        serverWebSocket.broadcast(msg);
    }

    @Override
    public void notifyUserHasARedCard(User currentUser) {
        UserHasARedCardMessage msg = new UserHasARedCardMessage(currentUser);
        serverWebSocket.broadcast(msg);
    }

    @Override
    public void notifyUserIsInDressingRoom(User currentUser) {
        UserIsInDressingRoomMessage msg = new UserIsInDressingRoomMessage(currentUser);
        serverWebSocket.broadcast(msg);
    }

    @Override
    public void notifySwitchTurn(int playerTurn, String sessionId) {
        SwitchTurnResultMessage msg = new SwitchTurnResultMessage(playerTurn, sessionId);
        serverWebSocket.broadcast(msg);
    }

    @Override
    public void notifyNotEnoughMoney(String sessionId) {
        NotEnoughMoneyMessage msg = new NotEnoughMoneyMessage();
        serverWebSocket.sendTo(sessionId, msg);
    }

    @Override
    public void notifyPropertyIsAlreadyOwned(int owner, String sessionId) {
        PropertyAlreadyOwnedMessage msg = new PropertyAlreadyOwnedMessage(owner);
        serverWebSocket.sendTo(sessionId, msg);
    }

    @Override
    public void notifyCardMessage(User user, String message, boolean result) {
        CardMessage msg = new CardMessage(user, message, result);
        serverWebSocket.sendTo(user.getSessionId(), msg);
    }

    @Override
    public void notifyUserIsBroke(User user) {
        BrokeMessage msg = new BrokeMessage(user);
        serverWebSocket.broadcast(msg);
    }

    @Override
    public void notifySquareMessage(User user, String message) {
        SquareMessage msg = new SquareMessage(user, message);
        serverWebSocket.broadcast(msg);
    }
}
