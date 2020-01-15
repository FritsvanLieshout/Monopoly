package server_interface;

import models.User;
import java.util.List;

public interface IServerMessageGenerator {

    void notifyUserAdded(String sessionId, String playerName);

    void notifyRegisterResult(String sessionId, boolean success);

    void notifyLoginResult(String sessionId, int userId);

    void notifyMoveUserMessage(int dice, String sessionId);

    void updateUserList(List<User> onlineUsers, String sessionId);

    void notifyStartGame();

    void updateCurrentUser(User user, String sessionId);

    void updateBoard(String sessionId);

    void notifyNonValueSquare(String sessionId);

    void notifyUserOverStart(String sessionId);

    void notifyPayRent(User currentUser, User ownedUser);

    void notifyUserHasARedCard(User currentUser);

    void notifyUserIsInDressingRoom(User currentUser);

    void notifySwitchTurn(int playerTurn, String sessionId);

    void notifyNotEnoughMoney(String sessionId);

    void notifyPropertyIsAlreadyOwned(int owner, String sessionId);

    void notifyCardMessage(User user, String message, boolean result);

    void notifyUserIsBroke(User user);

    void notifySquareMessage(User user, String message);
}
