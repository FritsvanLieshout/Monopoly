import models.Dice;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class DiceModelTest {

    Dice dice;

    @BeforeEach
    void setup() { dice = new Dice(); }

    @AfterEach
    void tearDown() {

    }

    /**
     * Example test for model Dice -> get number of dice
     */
    @Test
    void testModelDiceGetNumberOfDice() {
        var noDice = dice.getNofDice();

        Assertions.assertNotNull(noDice);
    }
}
