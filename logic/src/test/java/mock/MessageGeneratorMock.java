package mock;

import models.User;
import server_interface.IServerMessageGenerator;

import java.util.List;

public class MessageGeneratorMock implements IServerMessageGenerator {
    @Override
    public void notifyUserAdded(String sessionId, String playerName) { }

    @Override
    public void notifyRegisterResult(String sessionId, boolean success) { }

    @Override
    public void notifyLoginResult(String sessionId, int userId) { }

    @Override
    public void notifyMoveUserMessage(int dice, String sessionId) { }

    @Override
    public void updateUserList(List<User> onlineUsers, String sessionId) { }

    @Override
    public void notifyStartGame() { }

    @Override
    public void updateCurrentUser(User user, String sessionId) { }

    @Override
    public void updateBoard(String sessionId) { }

    @Override
    public void notifyNonValueSquare(String sessionId) { }

    @Override
    public void notifyUserOverStart(String sessionId) { }

    @Override
    public void notifyPayRent(User currentUser, User ownedUser) { }

    @Override
    public void notifyUserHasARedCard(User currentUser) { }

    @Override
    public void notifyUserIsInDressingRoom(User currentUser) { }

    @Override
    public void notifySwitchTurn(int playerTurn, String sessionId) { }

    @Override
    public void notifyNotEnoughMoney(String sessionId) { }

    @Override
    public void notifyPropertyIsAlreadyOwned(int owner, String sessionId) { }

    @Override
    public void notifyCardMessage(User user, String message, boolean result) { }

    @Override
    public void notifyUserIsBroke(User user) { }
}
