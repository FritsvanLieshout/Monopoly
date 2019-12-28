package models;

public class Board {

    private int currentTurn = 1;
    private User[] users;
    private Square[] squares;
    private String[] squareNames;

    public Board() {
        //users = new User[userCount]; //-> public Board(int userCount) -> new User[userCount]
        users = new User[1];
        squares = new Square[40];
        squareNames = new String[] { "Start", "Kane", "Community\nChest", "Heung-Min\nSon", "Income Tax", "Emirates\nStadium", "Insigne", "Change", "Koulibaly", "Mertens",
                "Dressing Room\nor Just Injured", "Rashford", "Vacation", "De Gea", "Pogba", "Old Trafford", "Matthijs de Ligt", "Community Chest", "Dybala", "Ronaldo",
                "Goal Bonus", "Salah", "Change", "Mane", "Big Virgil", "Anfield", "Suarez", "De Jong", "Nothing", "Messi",
                "Red Card", "Aguero", "Sterling", "Community Chest", "De Bruyne", "Etihad Stadium", "Change", "Mbappe", "Additional tax", "Neymar"};
    }

    public int getPositionOnBoard(int position) {
        return position % squares.length;
    }

    public Square[] getSquares() { return this.squares; }

    public String[] getSquareNames() { return this.squareNames; }

    public User getUser(int id) {
        return this.users[id];
    }

    public User getCurrentUser() {
        return users[currentTurn];
    }

    public int getCurrentTurn() {
        return currentTurn;
    }

    public void setCurrentTurn(int currentTurn) { this.currentTurn = currentTurn; }
}
