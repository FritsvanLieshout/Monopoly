import models.Board;
import models.Square;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class BoardModelTest {

    Board board;
    Square[] squares;
    String[] squareNames;

    @BeforeEach
    void setup() {
        board = new Board();
        squareNames = board.getSquareNames();
    }

    @AfterEach
    void tearDown() {

    }

    /**
     * Example test for model board -> get square names
     */
    @Test
    void testModelBoardGetSquareNames() {
        var squareNames = board.getSquareNames();

        Assertions.assertEquals(squareNames, board.getSquareNames());
    }

    /**
     * Example test for model board -> get size square names
     */
    @Test
    void testModelBoardGetSizeOfSquareNames() {
        var squareNameSize = squareNames.length;

        Assertions.assertEquals(squareNameSize, squareNames.length);
    }
}
