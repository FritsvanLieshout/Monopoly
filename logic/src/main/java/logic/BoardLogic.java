package logic;

import logic_interface.IBoardLogic;
import models.*;

public class BoardLogic implements IBoardLogic {

    private Board board;
    private User[] users;
    private Square[] squares;
    private String[] squareNames;

    public BoardLogic() {
        board = new Board();
        initBoard(board);
    }

    public void initBoard(Board board) {
        squares = board.getSquares();
        squareNames = board.getSquareNames();

        for (int i = 0; i < squareNames.length; i++) {
            if (i == 0) { initStartSquare(squares, squareNames, i); }
            else if (i == 10) { initDressingRoomSquare(squares, squareNames, i); }
            else if (i == 20) { initGoalBonusSquare(squares, squareNames, i); }
            else if (i == 30) { initRedCardSquare(squares, squareNames, i); }
            else if (i == 2 || i == 27 || i == 33) { initCommunityChestSquare(squares, squareNames, i); }
            else if (i == 7 || i == 22 || i == 36) { initChangeSquare(squares, squareNames, i); }
            else if (i == 4 || i == 12 || i == 28 || i == 38) { initSquare(squares, squareNames, i); }
            else if (i == 5 || i == 15 || i == 25 || i == 35) { initStadiumSquare(squares, squareNames, i); }
            else { initFootballPlayerSquare(squares, squareNames, i); }
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
        squares[i] = new RandomSquare(squareNames[i]);
        squares[i].setSquareId(i);
        squares[i].setSquareName(squareNames[i]);
    }

    private void initStadiumSquare(Square[] squares, String[] squareNames, int i) {
        squares[i] = new StadiumSquare(squareNames[i], 2000, -1, 500);
        squares[i].setSquareId(i);
        squares[i].setSquareName(squareNames[i]);
    }

    private void initFootballPlayerSquare(Square[] squares, String[] squareNames, int i) {
        //TODO: Fix calculating for the price of the player.
        int price = i * 50;
        squares[i] = new FootballPlayerSquare(squareNames[i], price, -1,price / 50 * 10 );
        squares[i].setSquareId(i);
        squares[i].setSquareName(squareNames[i]);
    }

    @Override
    public Square[] getSquares() {
        squares = board.getSquares();
        return squares;
    }

    @Override
    public Board getBoard() {
        return board;
    }
}
