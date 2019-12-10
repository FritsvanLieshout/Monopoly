package models;

import java.util.Random;

public class Dice {

    private int noDice;

    public void Dice(int noDice) {
        this.noDice = noDice;
    }

    public int getNofDice() {
        Random rnd = new Random();
        noDice = 1 + rnd.nextInt(6);
        return noDice;
    }
}
