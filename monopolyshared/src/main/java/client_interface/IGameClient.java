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
     * @param singlePlayer
     */
    void loginUser(String username, String password, boolean singlePlayer);
    /**
     * This method gives the user an true or false back for the register method
     * There will be show again a message.
     * @param success
     */
    void handleUserRegistrationResponse(boolean success);

    /**
     * Shows a message to the user that has been registered.
     * @param username
     */
    void handleUserRegistered(String username);

    /**
     * Show a message to the user if the login is success full.
     * Some buttons will be disabled after this.
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
     * With the given dice and sessionId the pawn of the user will be moved to a new place.
     * In the board the users old place will be replaced of the board to the new place of the user.
     * @param dice
     * @param sessionId
     */
    void handleMoveUserResponse(int dice, String sessionId);

    /**
     * Update the current list of the logic to the controller. If there is a new user they will be added to
     * this list. This list will be updated if there are some changes by a specific user.
     * @param users
     */
    void handleUserListResponse(List<User> users);

    /**
     * This method calls every user a message in the log when the game will start
     * if there are 4 users in the session.
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
     * The board will be updated if the current user with the given sessionId has
     * bought a property (football players or stadiums). The color will be set to
     * the color that has been set by the initializing.
     * @param sessionId
     */
    void handleUpdateBoard(String sessionId);

    /**
     * Send a message to the current user if the square where he / she became
     * is a non value square.
     */
    void handleNonValueSquareResponse();

    /**
     * Send a message to all users when the current user is over start.
     * The user will be updated from the logic to the controller of all users.
     * The money will be added to the user his / her wallet.
     */
    void handleUserIsOverStart();

    /**
     * Send a message to all users when a user became on an owned square by another user.
     * Both users wallet will be updated.
     * @param currentUser -> wallet: withDrawMoney(rentPrice);
     * @param ownedUser -> Wallet: addMoney(rentPrice);
     */
    void handlePayRentResponse(User currentUser, User ownedUser);

    /**
     * Send a message to all users when the current user became on the red card square.
     * The currentUser position on the board will be set to the dressing room square.
     * @param currentUser
     */
    void handleUserHasARedCardResponse(User currentUser);

    /**
     * Show a message if the current user is in dressing room. He / She needs to pay €500 and
     * need to wait in this turn at this square.
     * The user will be updated again at the end of this method.
     * @param currentUser
     */
    void handleUserIsInDressingRoomResponse(User currentUser);

    /**
     * This method gives the server the message to call the method in the game logic.
     * The current playerTurn will be given to change this in the logic and send to other users.
     * @param playerTurn
     */
    void endTurn(int playerTurn);

    /**
     * If it's user his / her turn. The buttons will be enabled else the buttons will be disabled.
     * @param playerTurn is the turn of the player right now.
     * @param sessionId is the sessionId of the current user.
     */
    void handleSwitchTurnResponse(int playerTurn, String sessionId);

    /**
     * Send a message to the user if he / she doesn't have enough money to the current user
     * if he / she wants to buy the property.
     */
    void handleNotEnoughMoneyResponse();

    /**
     * Send a message to the user if the square is already owned by another user of by himself / herself.
     * @param owner -> The user see which user has owned this property with the given owner id.
     */
    void handlePropertyAlreadyOwnedResponse(int owner);

    /**
     * Send a message to the user what the message is on the card. The message has been set in the logic.
     * Only the user sees this card message. The user will updated again (Money or isInDressingRoom()).
     */
    void handleCardMessageResponse(User user, String message, boolean result);

    /**
     * Send a message to all users if the current user is broke. If there are 3 users broke everyone gets
     * a message who the winner is.
     * @param user
     */
    void handleUserIsBrokeResponse(User user);

    /**
     * Sends a message to the user what kind of message it is. There are 3 squares for this message.
     * The text of this message has been set in the logic class.
     * @param user
     * @param message
     */
    void handleSquareMessageResponse(User user, String message);
}
