package controller;

import javafx.beans.binding.DoubleBinding;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.shape.Circle;
import javafx.fxml.Initializable;
import logic_factory.LogicFactory;
import logic_interface.*;
import models.*;

import java.io.File;
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

    private User user;
    private Board board;
    private ArrayList<User> users;
    private Square[][] boardSquares;
    private int squareCount = 0;
    private ArrayList<Square> squareList;

    public GameController() {
        logicFactory = new LogicFactory();
        iBoardLogic = logicFactory.getIBoardLogic();
        iGameLogic = logicFactory.getIGameLogic();
        users = new ArrayList<>();
        user = new User(1, "Kevin"); //TODO: this need the user that's logged in
        users.add(user);
        board = iBoardLogic.getBoard();
        boardSquares = new Square[10][10];
        squareList = iBoardLogic.getSquareList();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //initBoardForGUI(squares, gpMonopolyBoard);
        initLayoutBoard(gpMonopolyBoard);
        initSquaresToBoard(boardSquares, gpMonopolyBoard);

        btnThrowDice.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                moveUser();
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

//    private void initBoardForGUI(ArrayList<Square> squares, GridPane grid) {
//
//        for (int i = 0; i < squares.size(); i++) {
//            if (i < 10) {
//                grid.add(squares.get(i), i, 0);
//            } else if (i < 20) {
//                grid.add(squares.get(i), 10, 20 - i);
//            } else if (i < 30) {
//                grid.add(squares.get(i), i - 20, 0);
//            } else if (i < 40) {
//                grid.add(squares.get(i), 10, i - 30);
//            }
//            grid.setStyle("-fx-border-color: black;");
//            grid.getChildren().add(new Label(squares.get(i).getSquareName()));
//        }
//    }

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
        for (int y = 0; y < 10; y++) {
            for (int x = 0; x < 10; x++) {
                if (x == 0 || y == 0 || x == 9 || y == 9) {
                    if (squareCount <= squareList.size()) {
                        if (squareCount == 0) {
                            initSquares(squares, grid, x, y);
                        }
                    }
                    squareCount++;
                }
            }
        }
    }

    private void initSquares(Square[][] squares, GridPane grid, int x, int y) {
        Square s = squareList.get(squareCount);
        squares[x][y] = s;
        s.getChildren().add(new Label(s.getSquareName()));
        s.setStyle("-fx-border-color: black;");
        grid.add(s, x, y);
    }

    private void moveUser() {
        int dice1 = iGameLogic.getDice(dice);
        int dice2 = iGameLogic.getDice(dice);
        int noDice = dice1 + dice2;

        iGameLogic.moveUser(user, board, noDice);

        lblDice1.setText(Integer.toString(dice1));
        lblDice2.setText(Integer.toString(dice2));

        //TODO -> this check in the game logic
        if (user.getCurrentPlace() == 30) {
            iGameLogic.redCard(user);
        }
    }

    private void buyFootballPlayer() {
        iGameLogic.buyFootballPlayer(user, board);
    }

    private void endTurn() {
    }
}
