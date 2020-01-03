package client_interface;

import models.Square;
import models.User;

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
     *
     * @param username
     * @param password
     */
    void loginUser(String username, String password);
    /**
     *
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
     * @param token
     */
    void handleLoginResponse(String token);

    /**
     * The user goes the steps forward that are given by the dice.
     * In this method is an int newPlace. newPlace is called by this method
     * board.getPositionOnBoard(user.getCurrentPlace().
     * If checkIfUserIsInDressingRoom is false the newPlace is the same method
     * as above. But this time is the number of the dice added ofter this method.
     * Place of the user set by newPlace.
     * @param user
     * @param dice
     */
    void moveUser(User user, int dice);
}
