package logic;

import logic_interface.IGameLogic;
import models.*;

public class GameLogic implements IGameLogic {

    @Override
    public int getDice(Dice dice) {
        return dice.getNofDice();
    }

    @Override
    public void startBonus(User user) {
        user.getWallet().addMoneyToWallet(2000);
        Log.print(user,user.getUsername() + " is over start, 2000 added to " + user.getUsername() + " wallet");
    }

    @Override
    public void redCard(User user) {
        user.setPlace(10); //Need the coordinates of the dressing room.
        user.setInDressingRoom(true);
        Log.print(user, user.getUsername() + " receives a RED card! and has to go to the dressing room");
    }

    @Override
    public void buyFootballPlayer(User user, FootballPlayerSquare[] squares, int position) {
        int price = squares[position].getPrice();
        user.getWallet().withDrawMoneyOfWallet(price);
        squares[position].setOwner(user.getUserId());
        Log.print(user, user.getUsername() + "is the owner of square " + squares[position].getSquareName() + " \n for the price " + price + " and " + price + " is withdrawn from the wallet");
    }

    @Override
    public boolean checkIfUserWantToBuyPlayer(User user) {
        return false;
    }
}
