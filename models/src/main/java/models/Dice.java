package models;

import java.security.SecureRandom;

public class Dice {

    private int noDice;

    public Dice(int noDice) {
        this.noDice = noDice;
    }

    public int getNofDice() {
        SecureRandom rnd = new SecureRandom();
        noDice = 1 + rnd.nextInt(6);
        return noDice;
    }
}
