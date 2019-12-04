package models;

import java.util.ArrayList;

public class Board {

    User[] users;

    public Board() {

    }

    public User getUser(int id) {
        return users[id];
    }
}
