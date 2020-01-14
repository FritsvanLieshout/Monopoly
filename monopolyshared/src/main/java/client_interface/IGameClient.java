package client_interface;

import models.User;
import java.util.List;

/**
 * @author frits
 */
public interface IGameClient {

    /**
     *
     * @param gui
     */
    void registerClientGUI(IClientGUI gui);

    /**
     * This method register a new User.
     * @param username
     * @param password
     */
    void registerUser(String username, String password);

    /**
     * This method login a registered user.
     * @param username
     * @param password
     */
    void loginUser(String username, String password);
    /**
     * This method gives the user an true or false back for the register method
     * @param success
     */
    void handleUserRegistrationResponse(boolean success);

    /**
     *
     * @param username
     */
    void handleUserRegistered(String username);

    /**
     *
     * @param userId
     */
    void handleLoginResponse(int userId);

    /**
     * The user goes the steps forward that are given by the dice.
     * In this method is an int newPlace. newPlace is called by this method
     * board.getPositionOnBoard(user.getCurrentPlace().
     * If checkIfUserIsInDressingRoom is false the newPlace is the same method
     * as above. But this time is the number of the dice added ofter this method.
     * Place of the user set by newPlace.
     * @param dice
     */
    void moveUser(int dice);

    /**
     *
     * @param dice
     * @param sessionId
     */
    void handleMoveUserResponse(int dice, String sessionId);

    /**
     *
     * @param users
     */
    void handleUserListResponse(List<User> users);

    /**
     * This method call every user when the game will start if there are 4 users in the session.
     */
    void handleStartGameResponse();

    /**
     * This method gives an update of the user. -> for the controller.
     * @param user
     * @param sessionId
     */
    void handleUpdateCurrentUser(User user, String sessionId);

    /**
     * This method gives the server a message to call the method in the game logic.
     */
    void buyFootballPlayer();

    /**
     *
     * @param sessionId
     */
    void handleUpdateBoard(String sessionId);

    /**
     *
     */
    void handleNonValueSquareResponse();

    /**
     *
     */
    void handleUserIsOverStart();

    /**
     *
     * @param currentUser
     * @param ownedUser
     */
    void handlePayRentResponse(User currentUser, User ownedUser);

    /**
     *
     * @param currentUser
     */
    void handleUserHasARedCardResponse(User currentUser);

    /**
     *
     * @param currentUser
     */
    void handleUserIsInDressingRoomResponse(User currentUser);

    /**
     *
     */
    void endTurn(int playerTurn);

    /**
     *
     */
    void handleSwitchTurnResponse(int playerTurn, String sessionId);

    /**
     *
     */
    void handleNotEnoughMoneyResponse();

    /**
     *
     */
    void handlePropertyAlreadyOwnedResponse(int owner);

    /**
     *
     */
    void handleCardMessageResponse(User user, String message, boolean result);

    /**
     *
     * @param user
     */
    void handleUserIsBrokeResponse(User user);
}
