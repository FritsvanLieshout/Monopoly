package game_test;

import logic.BoardLogic;
import logic.GameLogic;
import logic_interface.IBoardLogic;
import logic_interface.IGameLogic;
import mock.MessageGeneratorMock;
import models.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import server_interface.IServerMessageGenerator;
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
    User user2;

    private IServerMessageGenerator messageGenerator = new MessageGeneratorMock();

    @BeforeEach
    void setup() {
        gameLogic = new GameLogic(messageGenerator);
        boardLogic = new BoardLogic();
        dice = new Dice();
        board = boardLogic.getBoard();
        users = new ArrayList<>();
        user = new User(1, "1", "Kevin");
        user2 = new User(2, "2", "Lisa");
        user.setPassword("I_Hate_Testing");
        user2.setPassword("I-Like");
        user2.getWallet().setMoney(-100);
        users.add(user);
        users.add(user2);
        gameLogic.login(user.getUsername(), user.getPassword(), user.getSessionId());
        gameLogic.login(user2.getUsername(), user2.getPassword(), user2.getSessionId());
        System.out.println("[Begin test] :");
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
        /**
         * Server needs to run.
         * var result = gameLogic.registerNewUser(user.getUsername(), user.getPassword(), user.getSessionId());
         * Assertions.assertTrue(result);
         */
    }

    /**
     * Example test for the login of the user.
     * This test call the method in the logic
     * The sessionId is needed, because I need this for the web socket communication
     */
    @Test
    void testLoginUser() {
        var result = gameLogic.login("John", "Doe", "3");
        Assertions.assertTrue(result);
    }

    /**
     * Example test for getDice in game logic.
     */
    @Test
    void testGetNoDice() {
        Dice dice = new Dice();
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
    void testMoveUserFailed() {
        int dice = 9;
        Square square = gameLogic.moveUser(dice, user.getSessionId());
        int position = square.getSquareId();
        String expect = board.getSquares()[dice].getSquareName();
        String actual = board.getSquares()[position].getSquareName();

        Assertions.assertEquals(expect, actual);
        Assertions.assertEquals(dice, position);
    }

    /**
     * Example test for to buy a square(aka: football player).
     * In the method here below there is a check. If the owner < 0,
     * the user can pay this square. But if the square is owned by
     * another user. This user can't by this square.
     */
    @Test
    void testBuyFootballPlayer() {
        int oldWallet = user.getWallet().getMoney();
        Square square = gameLogic.moveUser(11, user.getSessionId());
        gameLogic.buyFootballPlayer(user.getSessionId());

        for (Square s : board.getSquares()) {
            if (s.getSquareId() == square.getSquareId()) {
                user.getWallet().withDrawMoneyOfWallet(s.getPrice());
                s.setOwner(user.getUserId());
                Assertions.assertEquals(s.getOwner(), user.getUserId());
            }
        }
        int actualWallet = user.getWallet().getMoney();
        Assertions.assertNotEquals(oldWallet, actualWallet);
    }

    /**
     * Example test for to buy a square(aka: football player)
     */
    @Test
    void testBuyFootballPlayerThatIsOwned() {
        int oldWalletUser1 = user.getWallet().getMoney();
        Square square = gameLogic.moveUser(11, user2.getSessionId());
        gameLogic.buyFootballPlayer(user2.getSessionId());

        for (Square s : board.getSquares()) {
            if (s.getSquareId() == square.getSquareId()) {
                s.setOwner(user2.getUserId());
                if (s.getOwner() < 0) {
                    user.getWallet().withDrawMoneyOfWallet(s.getPrice());
                    s.setOwner(user.getUserId());
                    Assertions.assertNotEquals(s.getOwner(), user.getUserId());
                }
            }
        }

        gameLogic.moveUser(11, user.getSessionId());
        gameLogic.buyFootballPlayer(user.getSessionId());

        int actualWalletUser = user.getWallet().getMoney();
        Assertions.assertEquals(oldWalletUser1, actualWalletUser);
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
        int oldWalletUser = user.getWallet().getMoney();
        int oldWalletNewUser = user2.getWallet().getMoney();

        Square square = gameLogic.moveUser(6, user.getSessionId());
        user.setPlace(square.getSquareId());
        for (Square s : board.getSquares()) {
            if (s.getSquareId() == user.getCurrentPlace()) {
                s.setOwner(2);
                gameLogic.payRent(user, board);
                user.getWallet().withDrawMoneyOfWallet(s.getRentPrice());
                user2.getWallet().addMoneyToWallet(s.getRentPrice());

                Assertions.assertEquals(s.getOwner(), user2.getUserId());
            }
        }

        Assertions.assertNotEquals(oldWalletUser, user.getWallet().getMoney());
        Assertions.assertNotEquals(oldWalletNewUser, user2.getWallet().getMoney());
    }

    /**
     * Example test by starting a game, to check the size of the user that are online
     * This check will be called at the end of the login method.
     * In this case there are 4 users in the list of users in the game logic.
     * If there are 4 users the startGame method will be called.
     * @throws IllegalArgumentException
     */
    @Test
    void testCheckStartingConditionSuccess() {
        gameLogic.login("Frits", "Lies", "3");
        gameLogic.login("Bart", "Liesje", "4");
        var result = gameLogic.checkStartingCondition();
        Assertions.assertTrue(result);
    }

    /**
     * Example test by starting a game, to check the size of the user that are online
     * This check will be called at the end of the login method.
     * In this case there not enough users to start the game. There are only 2 users
     * instead of the required 4 users.
     */
    @Test
    void testCheckStartingConditionFalse() {
        var result = gameLogic.checkStartingCondition();
        Assertions.assertFalse(result);
    }

    /**
     * Example test before the register method.
     * This is an check if the username already exist in current session
     */
    @Test
    void testCheckIfUsernameAlreadyExists() {
        String username = "Lisa";
        var result = gameLogic.checkUserNameAlreadyExists(username);

        Assertions.assertTrue(result);
    }

    /**
     * Example test before the register method.
     * This is an check if the username already exist in current session
     */
    @Test
    void testCheckIfUsernameAlreadyDoesNotExists() {
        String username = "Jan";
        var result = gameLogic.checkUserNameAlreadyExists(username);

        Assertions.assertFalse(result);
    }

    /**
     * Example test called by the move user method in the logic
     * This check gives the user a money bonus if he / she is over start
     * In this test has the user became over the start.
     */
    @Test
    void testIfUserIsOverStart() {
        int oldWallet = user.getWallet().getMoney();
        int dice = 50;
        Square square = gameLogic.moveUser(dice, user.getSessionId());

        if (user.getCurrentPlace() + dice >= 40) {
            user.getWallet().addMoneyToWallet(2000);
            user.setPlace((user.getCurrentPlace() + dice) - 40);
        }

        int actualWallet = user.getWallet().getMoney();
        getLogInformation(user);

        Assertions.assertEquals(square.getSquareId(), user.getCurrentPlace());
        Assertions.assertNotEquals(oldWallet, actualWallet);
    }

    /**
     * Example test called by the move user method in the logic
     * This check gives the user a money bonus if he / she is over start
     * This test is if user the is not over start
     */
    @Test
    void testIfUserIsNotOverStart() {
        int oldWallet = user2.getWallet().getMoney();
        int dice = 5;
        Square square = gameLogic.moveUser(dice, user2.getSessionId());

        if (user2.getCurrentPlace() + dice >= 40) {
            user2.getWallet().addMoneyToWallet(2000);
            user2.setPlace((user2.getCurrentPlace() + dice) - 40);
        }
        else {
            user2.setPlace(user.getCurrentPlace() + dice);
        }

        int actualWallet = user2.getWallet().getMoney();
        getLogInformation(user2);

        Assertions.assertEquals(square.getSquareId(), user2.getCurrentPlace());
        Assertions.assertEquals(oldWallet, actualWallet);
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
        var result = gameLogic.varChecksRedCard(user);

        getLogInformation(user);

        Assertions.assertEquals(true, result);
        Assertions.assertTrue(result);
    }

    /**
     * Example test that will be called at the end of the moveUser method.
     * If the user became on the red card square (in this case squareNr 30)
     * The user will be redirect to the dressing room square (squareNr 10)
     * In this case the user became at this square.
     */
    @Test
    void testVARCheckRedCardFalse() {
        user2.setPlace(29);
        var result = gameLogic.varChecksRedCard(user2);

        getLogInformation(user2);

        Assertions.assertEquals(false, result);
        Assertions.assertFalse(result);
    }

    /**
     * Example test for a red card. This method has been called in the game logic class
     * After the check above this test (Exist in logic class) this method will be called
     */
    @Test
    void testRedCard() {
        gameLogic.redCard(user);
        boolean expectedIsInDressingRoom = user.isInDressingRoom();
        int expectPlace = user.getCurrentPlace();

        getLogInformation(user);

        Assertions.assertEquals(expectedIsInDressingRoom, user.isInDressingRoom());
        Assertions.assertEquals(expectPlace, user.getCurrentPlace());
    }

    /**
     * Example test of the user that will be called at the begin of the buyPlayer method
     * In this case the user has enough money in his/her wallet.
     */
    @Test
    void testIfUserHasEnoughMoney() {
        int price = 3000;
        int oldWallet = user.getWallet().getMoney();
        var result = gameLogic.checkForEnoughMoney(user, price);

        if (result) {
            user.getWallet().withDrawMoneyOfWallet(price);
        }

        int actualWallet = user.getWallet().getMoney();
        getLogInformation(user);

        Assertions.assertTrue(result);
        Assertions.assertNotEquals(oldWallet, actualWallet);
    }

    /**
     * Example test of the user that will be called at the begin of the buyPlayer method
     * In this case the user does't have enough money in his/her wallet.
     */
    @Test
    void testIfUserDoesNotHaveEnoughMoney() {
        int price = 10000;
        int oldWallet = user2.getWallet().getMoney();
        var result = gameLogic.checkForEnoughMoney(user2, price);
        int actualWallet = user2.getWallet().getMoney();

        getLogInformation(user2);

        Assertions.assertFalse(result);
        Assertions.assertEquals(oldWallet, actualWallet);
    }

    /**
     * Example test for the user if he's broke. This is an check if the user his / her wallet
     * is under the 0 Euro.
     * In this case the user is broke because the user haven't enough money in his / her wallet
     */
    @Test
    void testIfUserIsBroke() {
        var result = gameLogic.checkIfUserIsBroke(user2, board);
        getLogInformation(user2);

        Assertions.assertTrue(result);
    }

    /**
     * Example test for the user if he's broke. This is an check if the user his / her wallet
     * is under the 0 Euro.
     * In this case the user has enough money.
     */
    @Test
    void testIfUserIsNotBroke() {
        var result = gameLogic.checkIfUserIsBroke(user, board);
        getLogInformation(user2);

        Assertions.assertFalse(result);
    }

    /**
     * Example test for the random squares where you need to pay some money or get some money.
     * There are 3 squares of this. In this case I make a 3 turns to move a user. With the given dice.
     */
    @Test
    void testRandomSquare() {
        for (int t = 0; t < 3; t++) {
            if (t == 0) moveUserToSpecificSquare(4);
            else if (t == 1) moveUserToSpecificSquare(16);
            else if (t == 2) moveUserToSpecificSquare(18);
        }
    }

    /**
     * Example test for the community chest cards
     * There are 8 different messages with actions in the switch case statement. So I made a for loop to test this.
     */
    @Test
    void testCommunityChestCards() {
        for (int i = 0; i < 20; i++) {
            for (int turn = 1; turn < 5; turn++) {
                if (user2.getCurrentPlace() == 10) user2.setInDressingRoom(true);

                int noDice1 = getDice();
                int noDice2 = getDice();

                if (turn == 1) moveUserToSpecificSquare(noDice1 + noDice2);
                else if (turn == 2) moveUserToSpecificSquare(noDice1 + noDice2);
                else if (turn == 3) moveUserToSpecificSquare(noDice1 + noDice2);
                else if (turn == 4) moveUserToSpecificSquare(noDice1 + noDice2);
            }
        }
    }

    /**
     * Example test for the change cards
     * There are 8 different messages with actions in the switch case statement. So I made a for loop to test this.
     */
    @Test
    void testChangeCards() {
        for (int i = 0; i < 20; i++) {
            for (int turn = 1; turn < 5; turn++) {
                if (user.getCurrentPlace() == 10) user.setInDressingRoom(true);

                int noDice1 = getDice();
                int noDice2 = getDice();

                if (turn == 1) moveUserToSpecificSquare(noDice1 + noDice2);
                else if (turn == 2) moveUserToSpecificSquare(noDice1 + noDice2);
                else if (turn == 3) moveUserToSpecificSquare(noDice1 + noDice2);
                else if (turn == 4) moveUserToSpecificSquare(noDice1 + noDice2);
            }
        }
    }

    /**
     * Example test to check if an user is in the dressing room (in jail in real Monopoly)
     * In this case the user isn't in the dressing room.
     */
    @Test
    void testCheckIfUserIsNotInDressingRoom() {
        var result = gameLogic.checkIfUserIsInDressingRoom(user2);

        Assertions.assertFalse(result);
    }

    /**
     * Example test to check if an user is in the dressing room (in jail in real Monopoly)
     * In this case the user is in the dressing room.
     */
    @Test
    void testCheckIfUserIsInDressingRoom() {
        User userTest = new User(3, "3", "Test", "Tests");
        userTest.setInDressingRoom(true);
        var result = gameLogic.checkIfUserIsInDressingRoom(userTest);

        Assertions.assertTrue(result);
    }

    private void moveUserToSpecificSquare(int dice) {
        var square = gameLogic.moveUser(dice, user.getSessionId());
        user.setPlace(square.getSquareId());
        Assertions.assertEquals(square.getSquareId(), user.getCurrentPlace());
    }

    private int getDice() { return dice.getNofDice(); }

    private void getLogInformation(User user) {
        LOG.info("name: " + user.getUsername() + ", position: " + user.getCurrentPlace() + ", is in dressing room? " + user.isInDressingRoom() + ", is broke? " + user.isBroke());
    }
}
