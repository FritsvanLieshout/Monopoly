package models;

public class Board {

    private User[] users;
    private Square[] squares;
    private String[] squareNames;

    public Board() {
        squares = new Square[40];
        squareNames = new String[] { "Start", "Kane", "Community \n Chest", "Heung-Min Son", "Income Tax", "Emirates \n Stadium", "Insigne", "Change", "Koulibaly", "Mertens",
                "Dressing Room", "Rashford", "+ 1/3", "De Gea", "Pogba", "Old Trafford", "Matthijs \n de Ligt", "Community \n Chest", "Dybala", "Ronaldo",
                "Goal Bonus", "Salah", "Change", "Mane", "Big Virgil", "Anfield", "Suarez", "De Jong", "- 1/3", "Messi",
                "Red Card", "Aguero", "Sterling", "Community \n Chest", "De Bruyne", "Etihad \n Stadium", "Change", "Mbappe", "additional \n tax", "Neymar"};

        for (int i = 0; i < squareNames.length; i++) {
            if (i == 0) { initStartSquare(squares, squareNames, i); }
            else if (i == 10) { initDressingRoomSquare(squares, squareNames, i); }
            else if (i == 20) { initGoalBonusSquare(squares, squareNames, i); }
            else if (i == 30) { initRedCardSquare(squares, squareNames, i); }
            else if (i == 2 || i == 27 || i == 33) { initCommunityChestSquare(squares, squareNames, i); }
            else if (i == 7 || i == 22 || i == 36) { initChangeSquare(squares, squareNames, i); }
            else if (i == 4 || i == 12 || i == 28 || i == 38) { initSquare(squares, squareNames, i); }
            else if (i == 5 || i == 15 || i == 25 || i == 35) { initStadiumSquare(squares, squareNames, i); }
            else {
                //TODO: Fix calculating for the price of the player.
                initFootballPlayerSquare(squares, squareNames, i);
            }
        }
    }

    private void initStartSquare(Square[] squares, String[] squareNames, int i) {
        squares[i] = new StartSquare(squareNames[i]);
        squares[i].setSquareId(i);
        squares[i].setSquareName(squareNames[i]);
    }

    private void initDressingRoomSquare(Square[] squares, String[] squareNames, int i) {
        squares[i] = new DressingRoomSquare(squareNames[i]);
        squares[i].setSquareId(i);
        squares[i].setSquareName(squareNames[i]);
    }

    private void initGoalBonusSquare(Square[] squares, String[] squareNames, int i) {
        squares[i] = new GoalBonusSquare(squareNames[i], 2500);
        squares[i].setSquareId(i);
        squares[i].setSquareName(squareNames[i]);
    }

    private void initRedCardSquare(Square[] squares, String[] squareNames, int i) {
        squares[i] = new RedCardSquare(squareNames[i]);
        squares[i].setSquareId(i);
        squares[i].setSquareName(squareNames[i]);
    }

    private void initCommunityChestSquare(Square[] squares, String[] squareNames, int i) {
        squares[i] = new CommunityChestSquare(squareNames[i]);
        squares[i].setSquareId(i);
        squares[i].setSquareName(squareNames[i]);
    }

    private void initChangeSquare(Square[] squares, String[] squareNames, int i) {
        squares[i] = new ChangeSquare(squareNames[i]);
        squares[i].setSquareId(i);
        squares[i].setSquareName(squareNames[i]);
    }

    private void initSquare(Square[] squares, String[] squareNames, int i) {
        squares[i] = new Square(squareNames[i]);
        squares[i].setSquareId(i);
        squares[i].setSquareName(squareNames[i]);
    }

    private void initStadiumSquare(Square[] squares, String[] squareNames, int i) {
        squares[i] = new StadiumSquare(squareNames[i]);
        squares[i].setSquareId(i);
        squares[i].setSquareName(squareNames[i]);
    }

    private void initFootballPlayerSquare(Square[] squares, String[] squareNames, int i) {
        //TODO: Fix calculating for the price of the player.
        squares[i] = new PlayerSquare(squareNames[i], i * 50);
        squares[i].setSquareId(i);
        squares[i].setSquareName(squareNames[i]);
    }

    public int getPositionOnBoard(int position) {
        return position % squares.length;
    }

    public Square[] getSquares() { return this.squares; }


    public User getUser(int id) {
        return users[id];
    }
}
