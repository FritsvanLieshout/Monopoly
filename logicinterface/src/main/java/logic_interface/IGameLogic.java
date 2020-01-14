package logic_interface;

import models.Board;
import models.Dice;
import models.Square;
import models.User;

/**
 * Interface provided by the GameLogic class
 * @author frits
 */
public interface IGameLogic {
    /**
     * Get the number of the dice after button click.
     * The result of the number of dice is the number of steps that the user goes
     * forward. By a method call moverUser() in BoardLogic.
     * @param dice
     * @return the number of the dice.
     */
    int getDice(Dice dice);

    /**
     * If the user came on this tile. The user will be placed to 'in dressing room'.
     * @param user
     * The place of the user set to the id of the 'dressing room' square. Called by the method
     * user.setPlace(10). 10 is the id of the dressing room square. And another setter for
     * the boolean isUserInDressingRoom. Called by the method user.setInDressingRoom(true).
     */
    void redCard(User user);

    /**
     * If the square is not owned by himself or other players. The user has an option to buy a player that is linked
     * on a square. The square has a price. If the user buy this square, Inside the square is a setter of the owner
     * if the square isn't owned by anyone is this -1.
     * If the square has an owner this integer set to his/her userId.
     * @param sessionId
     */
    void buyFootballPlayer(String sessionId);

    /**
     * The user goes the steps forward that are given by the dice.
     * In this method is an int newPlace. newPlace is called by this method
     * board.getPositionOnBoard(user.getCurrentPlace().
     * If checkIfUserIsInDressingRoom is false the newPlace is the same method
     * as above. But this time is the number of the dice added ofter this method.
     * Place of the user set by newPlace.
     * Also is there an check if the square is owned by another user. This is called
     * by the method checkIfSquareIsOwned(). If the square is owned by another user
     * the user that became on this square has to pay rent to the user that owned the
     * square. The wallet of both users gets a update.
     * @param dice
     * @return the square where the user became through newPlace.
     */
    Square moveUser(int dice, String sessionId);

    /**
     * If the user end his/her turn
     * The user his/her id is the current turn number.
     * After this method the turn is + 1 with a limit of the size of list with users
     * @param playerTurn
     * @param sessionId
     */
    void switchTurn(int playerTurn, String sessionId);

    /**
     * Register method for a new user.
     * @param username
     * @param password
     * @param sessionId
     */
    boolean registerNewUser(String username, String password, String sessionId);

    /**
     * Login method for a registered user.
     * @param username
     * @param password
     * @param sessionId
     */
    boolean login(String username, String password, String sessionId);

    /**
     * Handle method for disconnected client.
     * @param sessionId
     */
    void processClientDisconnect(String sessionId);

    /**
     * Method for start a game if there are 4 users.
     */
    void startGame();

    /**
     * Update method to update the user for the controller.
     */
    void updateUsersInGame();

    /**
     * Method to check the starting condition after every login.
     */
    boolean checkStartingCondition();

    /**
     * Check for if the username already exist.
     * @param username
     * @return
     */
    boolean checkUserNameAlreadyExists(String username);

    /**
     * Pay rent to the user that owns that specific square.
     * @param user
     * @param board
     */
    void payRent(User user, Board board);

    /**
     * Check if the user is over start
     * If this is true, the user get €2000.
     * @param user
     * @param dice
     */
    boolean checkIfUserIsOverStart(User user, int dice);

    /**
     * A check for if the square is owned by another user of by yourself.
     * @param user
     * @param board
     * @return
     */
    boolean checkIfSquareIsOwned(User user, Board board);

    /**
     * A check if the user is in dressing room (in jail).
     * @param user
     * @return
     */
    boolean checkIfUserIsInDressingRoom(User user);

    /**
     * The VAR checks if the user gets a red card after his / her charge to an opponent.
     * @param user
     * @return
     */
    boolean varChecksRedCard(User user);

    /**
     * Check if the user has enough money to buy this property (football player or stadium)
     * @param currentUser
     * @param priceOfSquare
     * @return
     */
    boolean checkForEnoughMoney(User currentUser, int priceOfSquare);

    /**
     * A check for the user if he or she is broke.
     * @param user
     * @param board
     */
    boolean checkIfUserIsBroke(User user, Board board);

    /**
     * A check if the user became on one of this squares.
     * @param user
     * @param newPlace
     */
    void checkRandomSquares(User user, int newPlace);

    /**
     * If the user became on one of the community chest squares.
     * There will be send a message to the user with the action of the card (like go to start).
     * @param user
     */
    void doCommunityChestCardAction(User user);

    /**
     * If the user became on one of the change squares.
     * There will be send a message to the user with the action of the card (like go to start).
     * @param user
     */
    void doChangeCardAction(User user);
}
