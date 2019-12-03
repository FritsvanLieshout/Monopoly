package models;

import java.util.ArrayList;

public class Board {

    int totalUsers = 0;
    User[] users;
    Square[] squares = new Square[40];

    public Board(int totalUsers) {
        this.users = new User[totalUsers];
        this.totalUsers = totalUsers;

        for (int i = 0; i < squares.length; i++) {
            if (i == 0) {
                squares[i] = new StartSquare("Start");
            }
            else if (i == 10) {
                squares[i] = new DressingRoomSquare("Dressing Room | Just Injured");
            }
            else if (i == 20) {
                squares[i] = new GoalBonusSquare("Goal Bonus", 50);
            }
            else if (i == 30) {
                squares[i] = new RedCardSquare("Red Card!");
            }
            else {
                //todo
                squares[i] = new PlayerSquare(squares[i].getSquareName(), 0);
            }
        }
    }

    public Square moveUser(User user, int nofDice) {
        int newPlace = user.getCurrentPlace() + nofDice;
        user.setPlace(newPlace);
        //squares[newPlace].action(user, this);

        return squares[newPlace];
    }

    public int getTotalSquares() {
        return squares.length;
    }

    public User getUser(int id) {
        return users[id];
    }
}
