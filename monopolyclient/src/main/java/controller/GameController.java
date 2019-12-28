package controller;

import javafx.beans.binding.DoubleBinding;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.shape.Circle;
import javafx.fxml.Initializable;
import logic_factory.LogicFactory;
import logic_interface.*;
import models.*;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class GameController implements Initializable, IMonopolyGUI {

    public Circle colorPlayer1;
    public Circle colorPlayer2;
    public Circle colorPlayer3;
    public Circle colorPlayer4;
    public Label lblPlayer1;
    public Label lblPlayer2;
    public Label lblPlayer3;
    public Label lblPlayer4;
    public Label lblDice1;
    public Label lblDice2;
    public Button btnPlaceHouse;
    public Button btnPlaceHotel;
    public Button btnBuyPlayer;
    public TextArea taLog;
    public Label lblCurrentPlayerName;
    public Button btnThrowDice;
    public Button btnPayRent;
    public Button btnEndTurn;
    public GridPane gpMonopolyBoard;

    Dice dice = new Dice();

    private LogicFactory logicFactory;
    private IBoardLogic iBoardLogic;
    private IGameLogic iGameLogic;

    User user;
    User user2;

    private Board board;
    private ArrayList<User> users;
    private Square[][] boardSquares;
    private ArrayList<Square> squareList;

    public GameController() {
        logicFactory = new LogicFactory();
        iBoardLogic = logicFactory.getIBoardLogic();
        iGameLogic = logicFactory.getIGameLogic();
        users = new ArrayList<>();
        user = new User(1, "Kevin"); //TODO: this need the user that's logged in
        user2 = new User(2, "Lisa");
        users.add(user);
        users.add(user2);
        board = iBoardLogic.getBoard();
        boardSquares = new Square[11][11];
        squareList = iBoardLogic.getSquareList();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initLayoutBoard(gpMonopolyBoard);
        initSquaresToBoard(boardSquares, gpMonopolyBoard);
        setPawnOnBoard(0);

        btnThrowDice.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
//                if (board.getCurrentTurn() == user.getUserId()) {
                    moveUser();
//                }
//                else {
//                    Alert alert = new Alert(Alert.AlertType.INFORMATION, "It's not your turn!");
//                    alert.show();
//                }
            }
        });

        btnBuyPlayer.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                buyFootballPlayer();
            }
        });

        btnPayRent.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {

            }
        });

        btnEndTurn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                endTurn();
            }
        });
    }

    private void initLayoutBoard(GridPane grid)
    {
        RowConstraints rowsEdge = new RowConstraints();
        rowsEdge.setPercentHeight(14);
        RowConstraints rowsMid = new RowConstraints();
        rowsMid.setPercentHeight(8);

        ColumnConstraints colEdge = new ColumnConstraints();
        colEdge.setPercentWidth(14);
        ColumnConstraints colMid = new ColumnConstraints();
        colMid.setPercentWidth(8);

        grid.getColumnConstraints().addAll(colEdge, colMid, colMid, colMid, colMid, colMid, colMid, colMid, colMid, colMid, colEdge);
        grid.getRowConstraints().addAll(rowsEdge, rowsMid, rowsMid, rowsMid, rowsMid, rowsMid, rowsMid, rowsMid, rowsMid, rowsMid, rowsEdge);

        StackPane imagePane = new StackPane();
        grid.add(imagePane, 1, 1, 9, 9);

        DoubleBinding multipliedHeight = grid.heightProperty().multiply(0.72);
        DoubleBinding multipliedWidth = grid.widthProperty().multiply(0.72);

        imagePane.maxHeightProperty().bind(multipliedHeight);
        imagePane.maxWidthProperty().bind(multipliedWidth);
        imagePane.minHeightProperty().bind(multipliedHeight);
        imagePane.minWidthProperty().bind(multipliedWidth);
        imagePane.prefHeightProperty().bind(multipliedHeight);
        imagePane.prefWidthProperty().bind(multipliedWidth);

        final ImageView imageView = new ImageView("http://1.bp.blogspot.com/-Wjc79oqi1y0/VHitLAU44BI/AAAAAAAAG80/0UZ9n2JmvEo/s1600/Logo%2BMonopoly.png");
        imageView.setPreserveRatio(true);
        imageView.fitWidthProperty().bind(imagePane.widthProperty().divide(2));

        imagePane.setStyle("-fx-background-color: c8e0ca;");
        imagePane.getChildren().add(imageView);

        grid.setGridLinesVisible(false);
        grid.maxHeight(200.0);
        grid.maxWidth(200.0);
    }

    private void initSquaresToBoard(Square[][] squares, GridPane grid) {
        for (int i = 0; i < squareList.size(); i++) {
            if (i < 11) {
                initSquares(squares, grid, 10 - i, 10, i);
            }
            else if (i > 10 && i < 20) {
                initSquares(squares, grid, 0, 20 - i, i);
            }
            else if (i > 19 && i < 31) {
                initSquares(squares, grid, i - 20, 0, i);
            }
            else if (i > 30 && i < 40) {
                initSquares(squares, grid, 10, i - 30, i);
            }
        }
    }

    private void initSquares(Square[][] squares, GridPane grid, int x, int y, int squareId) {
        Square s = squareList.get(squareId);
        squares[x][y] = s;
        Label squareName = new Label(s.getSquareName());
        s.getChildren().add(squareName);
        s.setStyle("-fx-border-color: black;");
        grid.add(s, x, y);
    }

    private void setPawnOnBoard(int squareId) {
        squareList.get(squareId).setStyle("-fx-border-color: RED; -fx-border-width: 5");
    }

    private void movePawnOnBoard(int squareId, int playerNr) {
        //playerNr = user.userId
        //user.getColor();
        squareList.get(squareId).setStyle("-fx-border-color: RED; -fx-border-width: 5");
    }

    private void resetPawnOnBoard(int squareId, int playerNr) {
        squareList.get(squareId).setStyle("-fx-border-color: BLACK; -fx-border-width: 1");
    }

    //TODO -> This method checks if there are 2 to 4 user in the game. And set al the labels for the users in the game
    private void startGame() { }

    private void moveUser() {
        // if (board.getCurrentTurn() == user.getUserId()) {
            resetPawnOnBoard(user.getCurrentPlace(), user.getUserId());
            int dice1 = iGameLogic.getDice(dice);
            int dice2 = iGameLogic.getDice(dice);
            int noDice = dice1 + dice2;

            lblDice1.setText(Integer.toString(dice1));
            lblDice2.setText(Integer.toString(dice2));

            iGameLogic.moveUser(user, board, noDice);
            movePawnOnBoard(user.getCurrentPlace(), user.getUserId());

            if (user.getCurrentPlace() == 30) {
                iGameLogic.redCard(user);
            }
       // }
    }

    private void buyFootballPlayer() {
        iGameLogic.buyFootballPlayer(user, board);
    }

    private void endTurn() {
        iGameLogic.switchTurn(board, users);
    }
}
