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

    @Override
    public void action(User user, Board board) {
        //Log dat de user de pot krijgt.

        int goalBonus = getBonus();
        user.getWallet().addMoneyToWallet(goalBonus);
    }
}
