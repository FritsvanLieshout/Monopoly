package controller;

import client_interface.IClientGUI;
import client_interface.IGameClient;
import javafx.application.Platform;
import javafx.beans.binding.DoubleBinding;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.fxml.Initializable;
import javafx.scene.shape.Rectangle;
import logic_factory.LogicFactory;
import logic_interface.*;
import models.*;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class GameController implements Initializable, IClientGUI {

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
    User user3;
    User user4;

    private Board board;
    private ArrayList<User> users;
    private Square[][] boardSquares;
    private ArrayList<Square> squareList;

    Rectangle rec1 = new Rectangle(25, 25, Color.RED);
    Rectangle rec2 = new Rectangle(25, 25, Color.BLUE);
    Rectangle rec3 = new Rectangle(25, 25, Color.YELLOW);
    Rectangle rec4 = new Rectangle(25, 25, Color.ORANGE);

    private IGameClient gameClient;

    public GameController(IGameClient gameClient) {
        this.gameClient = gameClient;
        getGameClient().registerClientGUI(this);
        logicFactory = new LogicFactory();
        iBoardLogic = logicFactory.getIBoardLogic();
        iGameLogic = logicFactory.getIGameLogic();
        users = new ArrayList<>();
        user = new User(1, "Jan"); //TODO: this need the user that's logged in
        user2 = new User(2, "Lisa");
        user3 = new User(3, "Evi");
        user4 = new User(4, "Frank");
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
        rec1.setStrokeWidth(2);
        rec1.setStroke(Color.BLACK);
        rec2.setStrokeWidth(2);
        rec2.setStroke(Color.BLACK);
        rec3.setStrokeWidth(2);
        rec3.setStroke(Color.BLACK);
        rec4.setStrokeWidth(2);
        rec4.setStroke(Color.BLACK);
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
            public void handle(ActionEvent actionEvent) { register(); }
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
        //squareList.get(squareId).setStyle("-fx-border-color: RED; -fx-border-width: 5");
        //TODO -> fix this with more users -> squareList.get(squareId).setStyle("-fx-border-color: YELLOW; -fx-border-width: 5");

        squareList.get(squareId).setMargin(rec1, new Insets(-30, -30, 0, 0));
        squareList.get(squareId).setMargin(rec2, new Insets(-30, 30, 0, 0));
        squareList.get(squareId).setMargin(rec3, new Insets(30, -30, 0, 0));
        squareList.get(squareId).setMargin(rec4, new Insets(30, 30, 0, 0));
        squareList.get(squareId).getChildren().addAll(rec1, rec2, rec3, rec4);
    }

    private void movePawnOnBoard(int squareId, int playerNr) {
        //playerNr = user.getUserId
        //user.getColor();
        setPawn(squareId, playerNr);
    }

    private void setPawn(int squareId, int playerNr) {
        switch(playerNr) {
            case 1:
                squareList.get(squareId).getChildren().add(rec1);
                break;
            case 2:
                squareList.get(squareId).getChildren().add(rec2);
                break;
            case 3:
                squareList.get(squareId).getChildren().add(rec3);
                break;
            case 4:
                squareList.get(squareId).getChildren().add(rec4);
                break;
        }
    }

    private void setOwnerOfSquareColor(int squareId, int playerNr) {
        //-> playerNr = user.getUserId -> user.getColor();
        squareList.get(squareId).setStyle("-fx-background-color: RED; -fx-border-width: 1; -fx-border-color: BLACK");
    }

    //TODO -> This method checks if there are 2 to 4 user in the game. And set al the labels for the users in the game
    private void startGame() { }

    @Override
    public void moveUser() {
        // if (board.getCurrentTurn() == user.getUserId())
        int dice1 = iGameLogic.getDice(dice);
        int dice2 = iGameLogic.getDice(dice);
        int noDice = dice1 + dice2;
        int oldPlace = user.getCurrentPlace();

        lblDice1.setText(Integer.toString(dice1));
        lblDice2.setText(Integer.toString(dice2));

        iGameLogic.moveUser(user, board, noDice);

        if (oldPlace != user.getCurrentPlace()) {
            movePawnOnBoard(user.getCurrentPlace(), user.getUserId());
        }

        varCheckForARedCard(user);

        //Below here will be removed if web sockets are implemented!
//        int oldPlace2 = user2.getCurrentPlace();
//        int oldPlace3 = user3.getCurrentPlace();
//        int oldPlace4 = user4.getCurrentPlace();
//
//        iGameLogic.moveUser(user2, board, 8);
//        iGameLogic.moveUser(user3, board, 11);
//        iGameLogic.moveUser(user4, board, 3);
//
//        if (oldPlace2 != user2.getCurrentPlace()) {
//            movePawnOnBoard(user2.getCurrentPlace(), user2.getUserId());
//        }
//
//        if (oldPlace3 != user3.getCurrentPlace()) {
//            movePawnOnBoard(user3.getCurrentPlace(), user3.getUserId());
//        }
//
//        if (oldPlace4 != user4.getCurrentPlace()) {
//            movePawnOnBoard(user4.getCurrentPlace(), user4.getUserId());
//        }
//
//        varCheckForARedCard(user2);
//        varCheckForARedCard(user3);
//        varCheckForARedCard(user4);
    }

    @Override
    public void processRegistrationResponse(boolean response) {
        Platform.runLater(() -> {
            if (response) {
                showAlert("Monopoly", "Registration success!");
            }
            else {
                showAlert("Monopoly", "Registration failed!");
            }
        });
    }

    @Override
    public void processUserRegistered(String username) {
        showAlert("Monopoly", "name: " + username + " has been registered");
    }

    private void showAlert(String header, String content)
    {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle(header);
                alert.setHeaderText("Message for " + user.getUsername());
                alert.setContentText(content);
                alert.showAndWait();
            }
        });
    }

    public void register() { processLoginOrRegistration(false); }

    public void login() { processLoginOrRegistration(true); }

    public void processLoginOrRegistration(boolean isLogin) {
        String username = "Jan";
        String password = "I hate WebSockets";

        if (!isLogin) { getGameClient().registerUser(username, password); }
        else {
            //getGameClient().loginUser(username, password);
        }
    }

    public IGameClient getGameClient() { return gameClient; }

    private boolean varCheckForARedCard(User user) {
        if (user.getCurrentPlace() == 30) {
            iGameLogic.redCard(user);
            movePawnOnBoard(10, user.getUserId());  // -> squareId 10 = Dressing Room
            return true;
        }
        return false;
    }

    private void buyFootballPlayer() {
        iGameLogic.buyFootballPlayer(user, board);
        setOwnerOfSquareColor(user.getCurrentPlace(), user.getUserId());
    }

    private void endTurn() {
        //iGameLogic.switchTurn(board, users);
    }
    //ArrayList met Change and Community Chests -> Add Money, Withdraw Money and Go to a specific square
}
