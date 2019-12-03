package models;

public class GoalBonusSquare extends Square {

    private int bonus;

    public GoalBonusSquare(String name, int bonus) {
        super(name);
        this.bonus = bonus;
    }

    public int getBonus() {
        return bonus;
    }
}
