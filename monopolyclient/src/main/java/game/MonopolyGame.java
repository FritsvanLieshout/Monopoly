package game;

import models.Board;
import models.Dice;
import models.User;

import java.util.Random;

public class MonopolyGame {

    public void throwDice(Dice dice) {
        Random rnd = new Random();
        int nofDice = 1 + rnd.nextInt(6);
        dice.setNofDice(nofDice);
    }


    public void moveUser(User user, Dice dice, Board board) {
        throwDice(dice);
        board.moveUser(user, dice.getNofDice());
    }
}
