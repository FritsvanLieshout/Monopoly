package client_interface;

import models.User;
import java.util.List;

public interface IClientGUI {

    void moveUser();

    void processRegistrationResponse(boolean response);

    void processUserRegistered(String username);

    void processLoginResponse(int userId);

    void processMoveUserResponse(int dice, String sessionId);

    void processUserListResponse(List<User> users);

    void processStartGameResponse();

    void processUpdateUser(User user, String sessionId);

    void processUpdateBoardResponse(String sessionId);

    void processNonValueSquareResponse();

    void processUserIsOverStartResponse();

    void processPayRentResponse(User currentUser, User ownedUser);

    void processUserHasARedCardResponse(User currentUser);

    void processUserIsInDressingRoomResponse(User currentUser);

    void processSwitchTurnResponse(int playerTurn, String sessionId);

    void processNotEnoughMoneyResponse();

    void processAlreadyOwnedResponse(int owner);

    void processCardMessageResponse(User user, String message, boolean result);

    void processUserIsBrokeResponse(User user);

    void processSquareMessageResponse(User user, String message);
}
