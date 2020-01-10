import models.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class UserModelTest {

    User user;

    @BeforeEach
    void setup() {
        user = new User();
    }

    @AfterEach
    void tearDown() {

    }

    /**
     * Example test for model user -> set username
     */
    @Test
    void testModelUserSetUsername() {
        var username = "Bram";
        user.setUsername(username);

        Assertions.assertEquals(username, user.getUsername());
    }

    /**
     * Failed example test for model user -> set username
     */
    @Test
    void testModelUserSetUsernameFailed() {
        var username = "Jan";
        var usernameNewUser = "Sander";
        user.setUsername(usernameNewUser);
        Assertions.assertNotEquals(username, user.getUsername());
    }

    /**
     * Example test for model user -> set password
     */
    @Test
    void testModelUserSetPassword() {
        var password = "I_Hate_WebSockets!";
        user.setPassword(password);

        Assertions.assertEquals(password, user.getPassword());
    }

    /**
     * Failed example test for model user -> set username
     */
    @Test
    void testModelUserSetPasswordFailed() {
        var password = "I_Hate_WebSockets!";
        var passwordNewUser = "I_Love_WebSockets!";
        user.setPassword(passwordNewUser);

        Assertions.assertNotEquals(password, user.getPassword());
    }

    /**
     * Example test for model user -> set sessionId
     */
    @Test
    void testModelUserSetSessionId() {
        var sessionId = "1";
        user.setSessionId(sessionId);

        Assertions.assertEquals(sessionId, user.getSessionId());
    }

    /**
     * Example test for model user -> set is in dressing room
     */
    @Test
    void testModelUserSetIsInDressingRoom() {
        user.setInDressingRoom(true);

        Assertions.assertEquals(true, user.isInDressingRoom());
    }

    /**
     * Failed example test for model user -> set is in dressing room
     */
    @Test
    void testModelUserSetIsInDressingRoomFailed() {
        user.setInDressingRoom(false);

        Assertions.assertNotEquals(true, user.isInDressingRoom());
    }

    /**
     * Example test for model user -> set wallet
     */
    @Test
    void testModelUserSetWallet() {
        var money = 50000;
        user.getWallet().setMoney(money);

        Assertions.assertEquals(money, user.getWallet().getMoney());
    }

    /**
     * Example test for model user -> set place
     */
    @Test
    void testModelUserSetPlace() {
        var place = 9;
        user.setPlace(place);

        Assertions.assertEquals(place, user.getCurrentPlace());
    }

    /**
     * Example test for model user -> get username
     */
    @Test
    void testModelUserGetUsername() {
        var username = "John";
        user.setUsername(username);
        var currentUserName = user.getUsername();

        Assertions.assertEquals(currentUserName, user.getUsername());
    }

    /**
     *  Example test for model user -> get password
     */
    @Test
    void testModelUserGetPassword() {
        var password = "Test";
        user.setPassword(password);
        var currentPassword = user.getPassword();

        Assertions.assertEquals(currentPassword, user.getPassword());
    }

    /**
     *  Example test for model user -> get sessionId
     */
    @Test
    void testModelUserGetSessionId() {
        var sessionId = "1";
        user.setSessionId(sessionId);
        var currentSessionId = user.getSessionId();

        Assertions.assertEquals(currentSessionId, user.getSessionId());
    }

    /**
     *  Example test for model user -> get is in dressing room
     */
    @Test
    void testModelUserIsInDressingRoom() {
        user.setInDressingRoom(true);
        var currentIsInDressingRoom = user.isInDressingRoom();

        Assertions.assertEquals(currentIsInDressingRoom, user.isInDressingRoom());
    }

    /**
     *  Example test for model user -> get is in dressing room
     */
    @Test
    void testModelUserGetMoneyInWallet() {
        var currentMoneyInWallet = user.getWallet().getMoney();

        Assertions.assertEquals(currentMoneyInWallet, user.getWallet().getMoney());
    }
}
