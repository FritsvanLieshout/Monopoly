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
    public void notifyLoginResult(String sessionId, String token) {
        LoginResultMessage msg = new LoginResultMessage(token);
        serverWebSocket.sendTo(sessionId, msg);
    }

    @Override
    public void notifyMoveUserMessage(int dice, String sessionId) {
        MoveUserResultMessage msg = new MoveUserResultMessage(dice, sessionId);
        serverWebSocket.broadcast(msg);
        //broadcast
    }

    @Override
    public void updateUserList(List<User> onlineUsers, String sessionId) {
        UserListResultMessage msg = new UserListResultMessage(onlineUsers);
        serverWebSocket.sendTo(sessionId, msg);
    }

    @Override
    public void notifyStartGame() {
        serverWebSocket.broadcast(new StartGameMessage());
    }

    @Override
    public void updatePlaceOfCurrentUser(int currentPlace, String sessionId) {
        serverWebSocket.broadcast(new UpdatePlaceOfCurrentUserMessage(currentPlace, sessionId));
    }
}
