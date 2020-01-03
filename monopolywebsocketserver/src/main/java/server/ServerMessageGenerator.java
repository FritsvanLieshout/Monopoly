package server;

import messages.*;
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
        serverWebSocket.sendTo(sessionId, msg);
        //broadcast
    }

    @Override
    public void updateUsersInGame(List<String> usernameList, String sessionId) {
        UsersInGameResultMessage msg = new UsersInGameResultMessage(usernameList);
        serverWebSocket.sendTo(sessionId, msg);
    }
}
