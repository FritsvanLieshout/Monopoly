package user_test;

import models.Board;
import models.Dice;
import models.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for Monopoly game -> user tests.
 * @author frits
 */
class UserLogicTest {
    private Dice dice;
    private Board board;
    private User user;

    @BeforeEach
    void setup() {
        dice = new Dice();
        board = new Board();
        user = new User();
        System.out.println("[Begin test: User Tests]");
    }

    @AfterEach
    void tearDown() {

    }

    /**
     * Example test for login method of a registered user.
     * Result user logged in to the system.
     * TODO Assertions
     */
    @Test
    void testLoginSuccessFull() {
        user.setUsername("Lisa");
        user.setPassword("Hacker");
        //gameLogic.login(user.getUsername(), user.getUsername(), user.getSessionId());
    }

    /**
     * Example test for login method of a registered user.
     * Result user cannot login, because he/she doesn't set the password.
     * @throws IllegalArgumentException
     * TODO Assertions
     */
    @Test
    void testLoginFailed() {
        user.setUsername("Lisa");
        //empty password.
        //gameLogic.login(user.getUsername(), "", user.getSessionId());
    }

    /**
     * Example test for register of a new user.
     * userName is null.
     */
    @Test
    void testRegisterPlayerNameIsNull() {
        String password = "pwd";

        user.setUsername(null);
        user.setPassword(password);

        //Assertions.assertThrows(IllegalArgumentException.class, () -> gameLogic.registerNewUser(null, user.getUsername(), user.getSessionId()));
    }

    /**
     * Example test for register of a new user.
     * password is null.
     */
    @Test
    void testRegisterPasswordIsNull() {
        String name = "John";

        user.setUsername(name);
        user.setPassword(null);

        //Assertions.assertThrows(IllegalArgumentException.class, () -> gameLogic.registerNewUser(user.getUsername(), null, user.getSessionId()));
    }

    /**
     * Example test to get and set the user id.
      */
    @Test
    void testGetAndSetUserId() {
        int userId = 1;
        user.setUserId(userId);
        Assertions.assertEquals(userId, user.getUserId());
    }

    /**
     * Example test to get and set username
     */
    @Test
    void testGetAndSetUserName() {
        String name = "John";
        user.setUsername(name);
        Assertions.assertEquals(name, user.getUsername());
    }

    /**
     * Example test to set an user in a dressing room
     */
    @Test
    void testSetInDressingRoom() {
        user.setInDressingRoom(true);
        Assertions.assertEquals(true, user.isInDressingRoom());
    }

    /**
     * Example test to get information if the user is in the dressing room.
     */
    @Test
    void testSetOutDressingRoom() {
        user.setInDressingRoom(false);
        Assertions.assertEquals(false, user.isInDressingRoom());
    }

    /**
     *  Example test to get the money of the users wallet.
     *  At first I have add some money and withdraw some money.
     *  Result -> get wallet of the user.
     *  And the difference between actual wallet and current wallet
     *  after the money calculating (+/-)
     */
    @Test
    void testGetWalletOfUser() {
        int actual = user.getWallet().getMoney();
        user.getWallet().addMoneyToWallet(5000);
        user.getWallet().withDrawMoneyOfWallet(7000);
        user.getWallet().addMoneyToWallet(2100);

        Assertions.assertNotNull(user.getWallet().getMoney());
        Assertions.assertNotEquals(actual, user.getWallet().getMoney());
    }
}
