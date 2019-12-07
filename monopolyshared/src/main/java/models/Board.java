package models;

public class Board {

    User[] users;

    public Board() {

    }

    public User getUser(int id) {
        return users[id];
    }
}
