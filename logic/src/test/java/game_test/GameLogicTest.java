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

/**
 * Unit tests for Monopoly game -> game tests.
 * @author frits
 */
class GameLogicTest {

    private static final Logger LOG = LoggerFactory.getLogger(GameLogic.class);

    private IGameLogic gameLogic;
    private IBoardLogic boardLogic;
    Dice dice;
    Board board;
    User user;

    @BeforeEach
    void setup() {
        gameLogic = new GameLogic();
        boardLogic = new BoardLogic();
        dice = new Dice();
        board = boardLogic.getBoard();
        user = new User(1, "Kevin");
    }

    @AfterEach
    void tearDown() {

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
        //Assertions.assertThrows(NullPointerException.class, () -> gameLogic.getDice(dice));
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
        gameLogic.moveUser(user, board, (noDice1 + noDice2));
        int newPlace = user.getCurrentPlace();

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
            //TODO -> Assertions.assertThrows();
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
        User user2 = new User(2, "PayRentPls");
        int position2 = user2.getCurrentPlace();
        int newPosition2 = position2 + 8;

        board.getSquares()[newPosition2].setOwner(user2.getUserId());
        user2.getWallet().withDrawMoneyOfWallet(board.getSquares()[newPosition2].getPrice());

        int oldUser2Wallet = user2.getWallet().getMoney();

        if (board.getSquares()[newPosition2].getOwner() == user2.getUserId()) {
            LOG.debug(user2.getUsername() + " is the owner of " + board.getSquares()[newPosition2].getSquareName());
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
        }

        Assertions.assertNotEquals(oldUser1Wallet, user.getWallet().getMoney());
        Assertions.assertNotEquals(oldUser2Wallet, user2.getWallet().getMoney());
    }

    /**
     * Example test for pay rent to the user that owns the square.
     * The user that became on this square needs to pay some rent.
     * The owned user gets the rent of user 1.
     * The user starts at square 0. If he throws the dice the user goes
     * to a new position on the board.
     * @throws IllegalArgumentException user 1 cannot pay rent if the square isn't owned.
     * TODO
     */

    @Test
    void payRentIfSquareIsNotOwned() {

    }
}
