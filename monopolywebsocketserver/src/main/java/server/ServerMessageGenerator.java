package server;

import messages.*;
import models.Board;
import models.Square;
import models.User;
import server_interface.IServerMessageGenerator;
import server_interface.IServerWebSocket;

import java.util.ArrayList;
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
    public void notifyLoginResult(String sessionId, String token) {
        LoginResultMessage msg = new LoginResultMessage(token);
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
    public void notifyPayRent(User user) {
        PayRentMessage msg = new PayRentMessage(user);
        serverWebSocket.broadcast(msg);
    }
}
