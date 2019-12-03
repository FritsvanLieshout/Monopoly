package models;

import java.util.Random;

public class Dice {

    private int nofDice;

    public void Dice(int nofDice) {
        this.nofDice = nofDice;
    }

    public int getNofDice() { return nofDice; }

    public void setNofDice(int nofDice) { this.nofDice = nofDice; }
//    public int getNOFDice() {
//        Random rnd = new Random();
//        int nofDice = 1 + rnd.nextInt(6);
//        return nofDice;
//    }
}
