package logic_interface;

import models.Dice;
import models.FootballPlayerSquare;
import models.User;

/**
 * Interface provided by the GameLogic class
 * @author frits
 */
public interface IGameLogic {
    /**
     * Get the number of the dice after button click.
     * The result of the number of dice is the number of steps that the user goes
     * forward. By a method call moverUser() in BoardLogic.
     * @param dice
     * @return the number of the dice.
     */
    int getDice(Dice dice);

    /**
     * If the user at start or the user has been over the start square.
     * @param user
     * The result of this method is that the user get a 'weekly wage'
     * The weekly wage is 2500.
     * The weekly wage is added to the wallet of the user. By a method
     * call of getWallet().addMoneyToWallet(weekly_wage).
     */
    void startBonus(User user);

    /**
     * If the user came on this tile. The user will be placed to 'in dressing room'.
     * @param user
     * The place of the user set to the id of the 'dressing room' square. Called by the method
     * user.setPlace(10). 10 is the id of the dressing room square. And an other setter for
     * the boolean isUserInDressingRoom. Called by the method user.setInDressingRoom(true).
     */
    void redCard(User user);
    void buyFootballPlayer(User user, FootballPlayerSquare[] squares, int position);
    boolean checkIfUserWantToBuyPlayer(User user); //Need to be void buyFootballPlayer(User user), because the check need to be in the logic class.
}
