import models.Wallet;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class WalletModelTest {

    Wallet wallet;

    @BeforeEach
    void setup() { wallet = new Wallet(); }

    @AfterEach
    void tearDown() {

    }

    /**
     * Example test for model Wallet -> set price of wallet
     */
    @Test
    void testModelWalletSetMoney() {
        var money = 1000;
        wallet.setMoney(money);

        Assertions.assertEquals(money, wallet.getMoney());
    }

    /**
     * Failed example test for model Wallet -> set price of wallet
     */
    @Test
    void testModelWalletSetMoneyFailed() {
        var money = 1000;
        var newMoney = 4000;
        wallet.setMoney(newMoney);

        Assertions.assertNotEquals(money, wallet.getMoney());
    }

    /**
     * Example test for model Wallet -> add money to wallet
     */
    @Test
    void testModelWalletAddMoney2Wallet() {
        var money = 1000;
        wallet.addMoneyToWallet(money);
        var actual = wallet.getMoney();

        Assertions.assertEquals(actual, wallet.getMoney());
    }

    /**
     * Failed example test for model Wallet -> add money to wallet
     */
    @Test
    void testModelWalletAddMoney2WalletFailed() {
        var money = 1000;
        wallet.addMoneyToWallet(money);
        var actual = wallet.getMoney();
        var newMoney = 1800;
        wallet.addMoneyToWallet(newMoney);

        Assertions.assertNotEquals(actual, wallet.getMoney());
    }

    /**
     * Example test for model Wallet -> withdraw money of wallet
     */
    @Test
    void testModelWalletWithdrawMoneyOfWallet() {
        var money = 1000;
        wallet.withDrawMoneyOfWallet(money);
        var actual = wallet.getMoney();

        Assertions.assertEquals(actual, wallet.getMoney());
    }

    /**
     * Failed example test for model Wallet -> withdraw money of wallet
     */
    @Test
    void testModelWalletWithdrawMoneyOfWalletFailed() {
        var money = 1000;
        wallet.withDrawMoneyOfWallet(money);
        var actual = wallet.getMoney();
        var newMoney = 1800;
        wallet.withDrawMoneyOfWallet(newMoney);

        Assertions.assertNotEquals(actual, wallet.getMoney());
    }

    /**
     * Example test for model Wallet -> get money of wallet
     */
    @Test
    void testModelWalletGetMoney() {
        var moneyInWallet = wallet.getMoney();

        Assertions.assertEquals(moneyInWallet, wallet.getMoney());
    }
}
