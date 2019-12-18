package user_test;

import logic.UserLogic;
import logic_interface.IUserLogic;
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
    private IUserLogic userLogic;
    private Dice dice;
    private Board board;
    private User user;

    @BeforeEach
    void setup() {
        userLogic = new UserLogic();
        dice = new Dice();
        board = new Board();
        user = new User();
    }

    @AfterEach
    void tearDown() {

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

        Assertions.assertThrows(IllegalArgumentException.class, () -> userLogic.registerUser(user));
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

        Assertions.assertThrows(IllegalArgumentException.class, () -> userLogic.registerUser(user));
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
}
