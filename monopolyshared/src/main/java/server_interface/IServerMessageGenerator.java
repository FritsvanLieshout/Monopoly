package server_interface;

import models.User;

import java.util.List;

public interface IServerMessageGenerator {
    void notifyUserAdded(String sessionId, String playerName);

    void notifyRegisterResult(String sessionId, boolean success);

    void notifyLoginResult(String sessionId, String token);

    void notifyMoveUserMessage(int dice, String sessionId);

    void updateUsersInGame(List<String> usernameList, String sessionId);

    void updateUserList(List<User> onlineUsers, String sessionId);
}
