import models.Square;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class SquareModelTest {

    Square square;

    @BeforeEach
    void setup() {
        square = new Square();
    }

    @AfterEach
    void tearDown() {

    }

    /**
     * Example test for model Square -> set squareId
     */
    @Test
    void testModelSquareSetSquareId() {
        var squareId = 1;
        square.setSquareId(squareId);

        Assertions.assertEquals(squareId, square.getSquareId());
    }

    /**
     * Failed example test for model Square -> set squareId
     */
    @Test
    void testModelSquareSetSquareIdFailed() {
        var squareId = 1;
        var newSquareId = 10;
        square.setSquareId(newSquareId);

        Assertions.assertNotEquals(squareId, square.getSquareId());
    }

    /**
     * Example test for model Square -> set squareId
     */
    @Test
    void testModelSquareSetSquareName() {
        var squareName = "Sterling";
        square.setSquareName(squareName);

        Assertions.assertEquals(squareName, square.getSquareName());
    }

    /**
     * Failed example test for model Square -> set squareId
     */
    @Test
    void testModelSquareSetSquareNameFailed() {
        var squareName = "Sterling";
        var newSquareName = "Kane";
        square.setSquareName(newSquareName);

        Assertions.assertNotEquals(squareName, square.getSquareName());
    }

    /**
     * Example test for model Square -> set price of square
     */
    @Test
    void testModelSquareSetPriceOfSquare() {
        var price = 500;
        square.setPrice(price);

        Assertions.assertEquals(price, square.getPrice());
    }

    /**
     * Failed example test for model Square -> set price of square
     */
    @Test
    void testModelSquareSetPriceOfSquareFailed() {
        var price = 500;
        var newPrice = 1000;
        square.setPrice(newPrice);

        Assertions.assertNotEquals(price, square.getPrice());
    }

    /**
     * Example test for model Square -> set owner of square
     */
    @Test
    void testModelSquareSetOwnerOfSquare() {
        var ownerId = 1;
        square.setOwner(ownerId);

        Assertions.assertEquals(ownerId, square.getOwner());
    }

    /**
     * Failed example test for model Square -> set owner of square
     */
    @Test
    void testModelSquareSetOwnerOfSquareFailed() {
        var owner = 1;
        var newOwner = 2;
        square.setPrice(newOwner);

        Assertions.assertNotEquals(owner, square.getOwner());
    }

    /**
     * Example test for model Square -> set rent price of square
     */
    @Test
    void testModelSquareSetRentPriceOfSquare() {
        var rentPrice = 100;
        square.setRentPrice(rentPrice);

        Assertions.assertEquals(rentPrice, square.getRentPrice());
    }

    /**
     * Failed example test for model Square -> set rent price of square
     */
    @Test
    void testModelSquareSetRentPriceOfSquareFailed() {
        var rentPrice = 500;
        var newRentPrice = 1000;
        square.setRentPrice(newRentPrice);

        Assertions.assertNotEquals(rentPrice, square.getRentPrice());
    }

    /**
     * Example test for model square -> get square id
     */
    @Test
    void testModelSquareGetSquareId() {
        square.setSquareId(9);
        var squareId = square.getSquareId();

        Assertions.assertEquals(squareId, square.getSquareId());
    }

    /**
     * Example test for model square -> get name of square
     */
    @Test
    void testModelSquareGetSquareName() {
        square.setSquareName("Web Sockets");
        var squareName = square.getSquareName();

        Assertions.assertEquals(squareName, square.getSquareName());
    }

    /**
     * Example test for model square -> get price of square
     */
    @Test
    void testModelSquareGetSquarePrice() {
        square.setPrice(666);
        var price = square.getPrice();

        Assertions.assertEquals(price, square.getPrice());
    }

    /**
     * Example test for model square -> get owner of square
     */
    @Test
    void testModelSquareGetSquareOwner() {
        square.setOwner(4);
        var owner = square.getOwner();

        Assertions.assertEquals(owner, square.getOwner());
    }

    /**
     * Example test for model square -> get rent price of square
     */
    @Test
    void testModelSquareGetSquareRentPrice() {
        square.setRentPrice(75);
        var rentPrice = square.getRentPrice();

        Assertions.assertEquals(rentPrice, square.getRentPrice());
    }
}
