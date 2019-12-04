package models;

import java.util.ArrayList;

public class Board {

    int totalUsers = 0;
    User[] users;
    Square[] squares = new Square[40];

    public Board() {
    }

    public Square moveUser(User user, int nofDice1, int nofDice2) {
        int newPlace = user.getCurrentPlace() + (nofDice1 + nofDice2);
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
