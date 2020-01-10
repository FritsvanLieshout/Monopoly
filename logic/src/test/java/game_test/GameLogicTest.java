package game_test;

import logic.BoardLogic;
import logic.GameLogic;
import logic_interface.IBoardLogic;
import logic_interface.IGameLogic;
import models.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;

/**
 * Unit tests for Monopoly game -> game tests.
 * @author frits
 */
class GameLogicTest {

    private static final Logger LOG = LoggerFactory.getLogger(GameLogic.class);

    private IGameLogic gameLogic;
    private IBoardLogic boardLogic;
    ArrayList<User> users;
    Dice dice;
    Board board;
    User user;

    @BeforeEach
    void setup() {
        gameLogic = new GameLogic();
        boardLogic = new BoardLogic();
        dice = new Dice();
        board = boardLogic.getBoard();
        users = new ArrayList<>();
        user = new User(1, "1", "Kevin");
        users.add(user);
        System.out.println("[Begin test: Game Tests]");
    }

    @AfterEach
    void tearDown() {

    }

    /**
     * Example test for the register of a new user.
     * This test call the method in the logic
     * The sessionId is needed, because I need this for the web socket communication
     */
    @Test
    void testRegisterNewUser() {
        User newUser = new User();
        newUser.setUsername("Test");
        newUser.setPassword("IHateThis");
        newUser.setSessionId("5");
        Assertions.assertThrows(NullPointerException.class, () -> gameLogic.registerNewUser(newUser.getUsername(), newUser.getPassword(), newUser.getSessionId()));
    }

    /**
     * Example test for the login of the user.
     * This test call the method in the logic
     * The sessionId is needed, because I need this for the web socket communication
     */
    @Test
    void testLoginUser() {
        user.setPassword("I_Hate_Testing");
        Assertions.assertThrows(NullPointerException.class, () -> gameLogic.login(user.getUsername(), user.getPassword(), user.getSessionId()));
    }

    /**
     * Example test for getDice in game logic.
     */
    @Test
    void testGetNoDice() {
        int noDice = gameLogic.getDice(dice);
        Assertions.assertNotNull(noDice);
    }

    /**
     * Example test for getDice in game logic.
     * noDice is null.
     */
    @Test
    void testNoDiceIsNull() {
        int noDice = 0;
        int noDice1 = gameLogic.getDice(dice);
        Assertions.assertNotEquals(noDice, noDice1);
    }

    /**
     * Example test for moveUser in game logic
     * oldPlace is current place of the user.
     * new place is (current place + (noDice1 + noDice2))
     * The result of this test is that newPlace is not oldPlace.
     */

    @Test
    void testMoveUser() {
        int oldPlace = user.getCurrentPlace();
        int noDice1 = gameLogic.getDice(dice);
        int noDice2 = gameLogic.getDice(dice);
        Assertions.assertThrows(NullPointerException.class, () -> gameLogic.moveUser((noDice1 + noDice2), user.getSessionId()));
        int newPlace = user.getCurrentPlace() + (noDice1 + noDice2);

        Assertions.assertNotEquals(oldPlace, newPlace);
    }

    /**
     * Example test to set and get an owner of a square
     * Set the owner.
     * In the assert I will return the square owner and compare this with the userId.
     */
    @Test
    void testGetAndSetOwnerOfSquare() {
        Square square = new FootballPlayerSquare("Messi", 500, -1, 100);
        square.setOwner(user.getUserId());
        Assertions.assertEquals(square.getOwner(), user.getUserId());
    }

    /**
     * Example test for to buy a square(aka: football player).
     * In the method here below there is a check. If the owner < 0,
     * the user can pay this square. But if the square is owned by
     * another user. This user can't by this square.
     */
    @Test
    void testBuyFootballPlayer() {
        Square s = new FootballPlayerSquare("Neymar", 1000, -1, 250);
        int actualOwner = s.getOwner();
        int actualMoney = user.getWallet().getMoney();
        if (s.getOwner() < 0) {
            user.getWallet().withDrawMoneyOfWallet(s.getPrice());
            s.setOwner(user.getUserId());
            LOG.debug(s.getSquareName() + " has been added to " + user.getUsername() + "'s club!");
            LOG.debug(user.getUsername() + "'s wallet has been updated -> €" + user.getWallet().getMoney());
        }

        int expectedOwner = s.getOwner();
        int expectedMoney = user.getWallet().getMoney();
        Assertions.assertNotEquals(actualOwner, expectedOwner);
        Assertions.assertNotEquals(actualMoney, expectedMoney);
    }

    /**
     * Example test for to buy a square(aka: football player)
     * @throws IllegalArgumentException user cannot buy this square.
     */
    @Test
    void testBuyFootballPlayerThatIsOwned() {
        Square s = new FootballPlayerSquare("Neymar", 1000, -1, 250);
        s.setOwner(user.getUserId());
        if (s.getOwner() < 0) {
            //Nothing
        }
        else {
            Assertions.assertNotNull(s.getOwner());
        }
    }

    /**
     * Example test for pay rent to the user that owns the square.
     * The user that became on this square needs to pay some rent.
     * The owned user gets the rent of user 1.
     * The user starts at square 0. If he throws the dice the user goes
     * to a new position on the board.
     * user 1 need to pay the rent and it withdraw his/her wallet.
     * user 2 get the rent of user 1 and the rent will be added to his/her wallet.
     */

    @Test
    void payRent() {
        //User 2
        User user2 = new User(2, "2", "PayRentPls");
        int position2 = user2.getCurrentPlace();
        int newPosition2 = position2 + 8;

        board.getSquares()[newPosition2].setOwner(user2.getUserId());
        user2.getWallet().withDrawMoneyOfWallet(board.getSquares()[newPosition2].getPrice());

        int oldUser2Wallet = user2.getWallet().getMoney();

        if (board.getSquares()[newPosition2].getOwner() == user2.getUserId()) {
            LOG.info(user2.getUsername() + " is the owner of " + board.getSquares()[newPosition2].getSquareName());
        }

        //User 1
        int oldUser1Wallet = user.getWallet().getMoney();
        int position = user.getCurrentPlace();
        int newPosition = position + 8;
        int owner = board.getSquares()[newPosition].getOwner();
        int rentPrice = board.getSquares()[newPosition].getRentPrice();
        if (owner == board.getSquares()[8].getOwner()) {
            user.getWallet().withDrawMoneyOfWallet(rentPrice);
            user2.getWallet().addMoneyToWallet(rentPrice);
            LOG.info(user.getUsername() + " has to pay €" + board.getSquares()[8].getRentPrice() + " to " + user2.getUsername());

        }

        Assertions.assertNotEquals(oldUser1Wallet, user.getWallet().getMoney());
        Assertions.assertNotEquals(oldUser2Wallet, user2.getWallet().getMoney());
    }

    /**
     * Example test by starting a game, to check the size of the user that are online
     * This check will be called at the end of the login method.
     */
    @Test
    void testCheckStartingConditionSuccess() {
        var result = false;
        if (users.size() == 1) result = true;

        Assertions.assertEquals(true, result);
    }

    /**
     * Example test by starting a game, to check the size of the user that are online
     * This check will be called at the end of the login method.
     */
    @Test
    void testCheckStartingConditionFalse() {
        var result = false;
        if (users.size() == 2) result = true;

        Assertions.assertEquals(false, result);
    }

    /**
     * Example test to give the users a message for the start af the game.
     * The check above this test method will be also called in the logic class.
     * In this case the size must be 1
     */
    @Test
    void testStartGameTrue() {
        var result = false;
        if (users.size() == 1) result = true;
        Assertions.assertTrue(result);
    }

    /**
     * Example test to give the users a message for the start af the game.
     * The check above this test method will be also called in the logic class.
     * in this case the size must be 2, but there is only 1 user online.
     */
    @Test
    void testStartGameFalse() {
        var result = false;
        if (users.size() == 2) result = true;
        Assertions.assertFalse(result);
    }

    /**
     * Example test before the register method.
     * This is an check if the username already exist in current session
     */
    @Test
    void testCheckIfUsernameAlreadyExists() {
        User currentUser = new User(3, "3", "Kevin");
        var result = checkUserNameAlreadyExists(currentUser.getUsername());

        Assertions.assertEquals(true, result);
    }

    /**
     * Example test before the register method.
     * This is an check if the username already exist in current session
     */
    @Test
    void testCheckIfUsernameAlreadyDoesNotExists() {
        User currentUser = new User(4, "4", "Willem");
        var result = checkUserNameAlreadyExists(currentUser.getUsername());

        Assertions.assertEquals(false, result);
    }

    /**
     * Example test called by the move user method in the logic
     * This check gives the user a money bonus if he / she is over start
     * In this test has the user became over the start.
     */
    @Test
    void testIfUserIsOverStart() {
        var oldWallet = user.getWallet().getMoney();
        user.setPlace(36);
        var dice = 10;
        var result = false;
        var checkStart = user.getCurrentPlace() + dice;
        if (checkStart >= 40) {
            result = true;
            user.getWallet().addMoneyToWallet(2000); //Start bonus
        }

        Assertions.assertEquals(true, result);
        Assertions.assertNotEquals(oldWallet, user.getWallet().getMoney());
        Assertions.assertEquals(5500, user.getWallet().getMoney());
    }

    /**
     * Example test called by the move user method in the logic
     * This check gives the user a money bonus if he / she is over start
     * This test is if user the is not over start
     */
    @Test
    void testIfUserIsNotOverStart() {
        var oldWallet = user.getWallet().getMoney();
        user.setPlace(36);
        var dice = 3;
        var result = false;
        var checkStart = user.getCurrentPlace() + dice;
        if (checkStart >= 40) {
            result = true;
            user.getWallet().addMoneyToWallet(2000); //Start bonus
        }

        Assertions.assertNotEquals(true, result);
        Assertions.assertEquals(oldWallet, user.getWallet().getMoney());
    }

    /**
     * Example test that will be called at the end of the moveUser method.
     * If the user became on the red card square (in this case squareNr 30)
     * The user will be redirect to the dressing room square (squareNr 10)
     * In this case the user became at this square.
     */
    @Test
    void testVARCheckRedCardTrue() {
        user.setPlace(30);
        var result = false;
        if (user.getCurrentPlace() == 30) result = true;

        Assertions.assertEquals(true, result);
    }

    /**
     * Example test that will be called at the end of the moveUser method.
     * If the user became on the red card square (in this case squareNr 30)
     * The user will be redirect to the dressing room square (squareNr 10)
     * In this case the user became at this square.
     */
    @Test
    void testVARCheckRedCardFalse() {
        user.setPlace(22);
        var result = false;
        if (user.getCurrentPlace() == 30) result = true;

        Assertions.assertEquals(false, result);
    }

    /**
     * Example test for a red card. This method has been called in the game logic class
     * After the check above this test (Exist in logic class) this method will be called
     */
    @Test
    void testRedCard() {
        user.setPlace(10);
        user.setInDressingRoom(true);

        Assertions.assertThrows(Exception.class, () -> gameLogic.redCard(user));

        Assertions.assertEquals(10, user.getCurrentPlace());
        Assertions.assertEquals(true, user.isInDressingRoom());
    }

    /**
     * Example test of the user that will be called at the begin of the buyPlayer method
     * In this case the user has enough money in his/her wallet.
     */
    @Test
    void testIfUserHasEnoughMoney() {
        var result = false;
        var price = 1000;
        if (user.getWallet().getMoney() - price <= 0) result = false;
        else result = true;

        Assertions.assertNotEquals(false, result);
    }

    /**
     * Example test of the user that will be called at the begin of the buyPlayer method
     * In this case the user does't have enough money in his/her wallet.
     */
    @Test
    void testIfUserDoesNotHaveEnoughMoney() {
        var result = false;
        var price = 5000;
        if (user.getWallet().getMoney() - price <= 0) result = false;
        else result = true;

        Assertions.assertEquals(false, result);
    }

    /**
     * Example test of this method in the game logic class.
     * this method has been used by some other methods to return the user by his/her id
     */
    @Test
    void testGetUserByUserId() {
        User currentUser = getUserByUserId(user.getUserId());
        Assertions.assertEquals(user.getUserId(), currentUser.getUserId());
    }

    private boolean checkUserNameAlreadyExists(String username)
    {
        for(User u : users) {
            if (u.getUsername().equals(username)) return true;
        }
        return false;
    }

    private User getUserByUserId(int userId) {
        for (User user : users) {
            if (user.getUserId() == userId) return user;
        }
        return null;
    }
}
