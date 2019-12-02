package models;

import java.util.Random;

public class Dice {

    public int getNOFDice() {
        Random rnd = new Random();
        int nofDice = 1 + rnd.nextInt(6);
        return nofDice;
    }
}
