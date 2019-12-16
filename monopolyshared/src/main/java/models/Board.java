package models;

public class Board {

    private int currentTurn;
    private User[] users;
    private Square[] squares;
    private String[] squareNames;

    public Board() {
        squares = new Square[40];
        squareNames = new String[] { "Start", "Kane", "Community Chest", "Heung-Min Son", "Income Tax", "Emirates Stadium", "Insigne", "Change", "Koulibaly", "Mertens",
                "Dressing Room | Just Injured", "Rashford", "Vacation", "De Gea", "Pogba", "Old Trafford", "Matthijs de Ligt", "Community Chest", "Dybala", "Ronaldo",
                "Goal Bonus", "Salah", "Change", "Mane", "Big Virgil", "Anfield", "Suarez", "De Jong", "Rest", "Messi",
                "Red Card", "Aguero", "Sterling", "Community Chest", "De Bruyne", "Etihad Stadium", "Change", "Mbappe", "additional tax", "Neymar"};
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

    public void switchTurn() {
        if (++currentTurn >= users.length) {
            currentTurn = 0;
        }
    }
}
