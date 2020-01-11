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
import logic.BoardLogic;
import logic_interface.*;
import models.*;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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
    public ListView lvLog;
    public Label lblCurrentPlayerName;
    public Button btnThrowDice;
    public Button btnEndTurn;
    public GridPane gpMonopolyBoard;
    public TextField tfUsername;
    public PasswordField tfPassword;
    public Button btnLogin;
    public Button btnRegister;

    Dice dice = new Dice();

    private IBoardLogic iBoardLogic;

    private Board board;
    private ArrayList<User> users;
    private Square[][] boardSquares;
    private static ArrayList<Square> squareList;

    private int playerTurn = 0;
    private int playerNr = 0;

    Rectangle rec1 = new Rectangle(25, 25, Color.RED);
    Rectangle rec2 = new Rectangle(25, 25, Color.BLUE);
    Rectangle rec3 = new Rectangle(25, 25, Color.YELLOW);
    Rectangle rec4 = new Rectangle(25, 25, Color.ORANGE);

    private IGameClient gameClient;

    public GameController(IGameClient gameClient) {
        this.gameClient = gameClient;
        getGameClient().registerClientGUI(this);
        iBoardLogic = new BoardLogic();
        users = new ArrayList<>();
        board = iBoardLogic.getBoard();
        boardSquares = new Square[11][11];
        squareList = iBoardLogic.getSquareList();
    }

    public IGameClient getGameClient() { return gameClient; }

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
        setDisable(true);

        btnLogin.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                login();
            }
        });

        btnRegister.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                register();
            }
        });

        btnThrowDice.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                moveUser();
            }
        });

        btnBuyPlayer.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) { buyFootballPlayer(); }
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
        squareList.get(squareId).setMargin(rec1, new Insets(-30, -30, 0, 0));
        squareList.get(squareId).setMargin(rec2, new Insets(-30, 30, 0, 0));
        squareList.get(squareId).setMargin(rec3, new Insets(30, -30, 0, 0));
        squareList.get(squareId).setMargin(rec4, new Insets(30, 30, 0, 0));
        squareList.get(squareId).getChildren().addAll(rec1, rec2, rec3, rec4);
    }

    private void movePawnOnBoard(int squareId, int playerNr) {
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
            default:
                showAlert("Player number doesn't exist");
                break;
        }
    }

    private void setOwnerOfSquareColor(int squareId, int playerNr) {
        Platform.runLater(() -> {
            switch(playerNr) {
                case 1:
                    squareList.get(squareId).setStyle("-fx-background-color: RED; -fx-border-width: 1; -fx-border-color: BLACK");
                    break;
                case 2:
                    squareList.get(squareId).setStyle("-fx-background-color: BLUE; -fx-border-width: 1; -fx-border-color: BLACK");
                    break;
                case 3:
                    squareList.get(squareId).setStyle("-fx-background-color: YELLOW; -fx-border-width: 1; -fx-border-color: BLACK");
                    break;
                case 4:
                    squareList.get(squareId).setStyle("-fx-background-color: ORANGE; -fx-border-width: 1; -fx-border-color: BLACK");
                    break;
                default:
                    showAlert("Player number doesn't exist");
                    break;
            }
        });
    }

    @Override
    public void moveUser() {
        if (playersTurn()) {
            int dice1 = dice.getNofDice();
            int dice2 = dice.getNofDice();
            int noDice = dice1 + dice2;

            lblDice1.setText(Integer.toString(dice1));
            lblDice2.setText(Integer.toString(dice2));

            getGameClient().moveUser(noDice);
        }
    }

    @Override
    public void processRegistrationResponse(boolean response) {
        Platform.runLater(() -> {
            if (response) {
                showAlert("Registration success!");
            }
            else {
                showAlert("Registration failed, please try again!");
            }
        });
    }

    @Override
    public void processUserRegistered(String username) {
        lvLog.getItems().add(getDate() + ": " +  username + " has been registered!");
    }

    @Override
    public void processLoginResponse(int userId) {
        Platform.runLater(() -> {
            showAlert("Login Success, waiting for other opponent(s)");
            btnLogin.setDisable(true);
            btnRegister.setDisable(true);
            tfUsername.setDisable(true);
            tfPassword.setDisable(true);
            this.playerNr = userId;
        });
    }

    @Override
    public void processMoveUserResponse(int dice, String sessionId) {
        Platform.runLater(() -> {
            User currentUser = getUserBySessionId(sessionId);
            movePawnOnBoard(currentUser.getCurrentPlace(), currentUser.getUserId());
            String msg = currentUser.getUsername() + " has dice " + dice + " and goes to " + board.getSquares()[currentUser.getCurrentPlace()].getSquareName();
            lvLog.getItems().add(getDate() + ": " + msg);
            btnThrowDice.setDisable(true);
        });
    }

    @Override
    public void processUserListResponse(List<User> users) {
        Platform.runLater(() -> {
            for (User user : users) {
                if (!checkUserNameAlreadyExists(user.getUsername())) {
                    this.users.add(user);
                    if (user.getUserId() == 1) {
                        lblPlayer1.setText(user.getUsername());
                    } else if (user.getUserId() == 2) {
                        lblPlayer2.setText(user.getUsername());
                    } else if (user.getUserId() == 3) {
                        lblPlayer3.setText(user.getUsername());
                    } else {
                        lblPlayer4.setText(user.getUsername());
                    }
                }
            }
        });
    }

    @Override
    public void processStartGameResponse() {
        Platform.runLater(() -> {
            lvLog.getItems().add(getDate() + ": The game starts now!");
            playerTurn = 1;
            if (playerNr == playerTurn) setDisable(false);
            else setDisable(true);
        });
    }

    @Override
    public void processUpdateUser(User user, String sessionId) {
        Platform.runLater(() -> {
            User currentUser = getUserBySessionId(sessionId);
            currentUser.setPlace(user.getCurrentPlace());
            currentUser.setInDressingRoom(user.isInDressingRoom());
            currentUser.setWallet(user.getWallet());
            Square s = getSquare(currentUser.getCurrentPlace());
            lvLog.getItems().add(getDate() + ": " + currentUser.getUsername() + ", position on board: " + s.getSquareName());
        });
    }

    @Override
    public void processUpdateBoardResponse(String sessionId) {
        Platform.runLater(() -> {
            User currentUser = getUserBySessionId(sessionId);
            Square s = getSquare(currentUser.getCurrentPlace());
            s.setOwner(currentUser.getUserId());
            setOwnerOfSquareColor(currentUser.getCurrentPlace(), currentUser.getUserId());
            lvLog.getItems().add(getDate() + ": " + s.getSquareName() + " has been added to " + currentUser.getUsername() + "'s club!");
        });
    }

    @Override
    public void processNonValueSquareResponse() {
        Platform.runLater(() -> {
            showAlert("The square where you became is a non value square");
        });
    }

    @Override
    public void processUserIsOverStartResponse() {
        Platform.runLater(() -> {
            lvLog.getItems().add(getDate() + ": " + " You are over start, €2000 added to your wallet");
        });
    }

    @Override
    public void processPayRentResponse(User currentUser, User ownedUser) {
        Platform.runLater(() -> {
            Square s = getSquare(currentUser.getCurrentPlace());
            var msg = ", your wallet has been updated -> €";
            lvLog.getItems().add(getDate() + " -> " + currentUser.getUsername() + " has to pay €" + s.getRentPrice() + " rent to " + ownedUser.getUsername());
            User newOwnedUser = getUserBySessionId(ownedUser.getSessionId());
            newOwnedUser.setWallet(ownedUser.getWallet());
            lvLog.getItems().add(getDate() + " -> " + newOwnedUser.getUsername() + msg + newOwnedUser.getWallet().getMoney());
            User newCurrentUser = getUserBySessionId(currentUser.getSessionId());
            newCurrentUser.setWallet(currentUser.getWallet());
            lvLog.getItems().add(getDate() + " -> " + newCurrentUser.getUsername() + msg + newCurrentUser.getWallet().getMoney());
        });
    }

    @Override
    public void processUserHasARedCardResponse(User currentUser) {
        Platform.runLater(() -> {
            processUpdateUser(currentUser, currentUser.getSessionId());
            lvLog.getItems().add(getDate() + " -> " + currentUser.getUsername() + " receives a RED Card! and has to go to the dressing room!");
            movePawnOnBoard(currentUser.getCurrentPlace(), currentUser.getUserId());
        });
    }

    @Override
    public void processUserIsInDressingRoomResponse(User currentUser) {
        Platform.runLater(() -> {
            Square square = getSquare(currentUser.getCurrentPlace());
            lvLog.getItems().add(getDate() + " -> " + currentUser.getUsername() + " stays this round at " + square.getSquareName() + " and need to pay €500");
            processUpdateUser(currentUser, currentUser.getSessionId());
        });
    }

    @Override
    public synchronized void processSwitchTurnResponse(int playerTurn, String sessionId) {
        Platform.runLater(() -> {
            this.playerTurn = playerTurn;
            User currentUser = getUserByUserId(playerTurn);
            lvLog.getItems().add("");
            lvLog.getItems().add(getDate() + " -> " + currentUser.getUsername() + " it's your turn!");
            if (playerNr == playerTurn) setDisable(false);
            else setDisable(true);
        });
    }

    @Override
    public void processNotEnoughMoneyResponse() {
        Platform.runLater(() -> {
            showAlert("You don't have enough money to buy this property");
        });
    }

    @Override
    public void processAlreadyOwnedResponse() {
        Platform.runLater(() -> {
            showAlert("This property is already owned by your opponents or yourself!");
        });
    }

    private void showAlert(String content)
    {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Monopoly");
                alert.setContentText(content);
                alert.showAndWait();
            }
        });
    }

    public void register() { processLoginOrRegistration(false); }

    public void login() { processLoginOrRegistration(true); }

    public void processLoginOrRegistration(boolean isLogin) {
        String username = tfUsername.getText();
        String password = tfPassword.getText();
        lblCurrentPlayerName.setText(username);

        if(username.equals(""))
        {
            showAlert("Invalid username");
        }
        else if (password.equals("")) {
            showAlert("Invalid password");
        }
        else {
            if (!isLogin) {
                getGameClient().registerUser(username, password);
            } else {
                getGameClient().loginUser(username, password);
            }
        }
    }

    private void buyFootballPlayer() {
        if (playersTurn()) {
            getGameClient().buyFootballPlayer();
        }
    }

    private void endTurn() {
        if (playersTurn()) {
            getGameClient().endTurn(playerTurn);
            switchTurn();
            setDisable(true);
        }
    }

    private void setDisable(boolean disable) {
        btnThrowDice.setDisable(disable);
        btnEndTurn.setDisable(disable);
        btnBuyPlayer.setDisable(disable);
        btnPlaceHotel.setDisable(disable);
        btnPlaceHouse.setDisable(disable);
    }

    private User getUserBySessionId(String sessionId) {
        for (User user : users) {
            if (user.getSessionId().equals(sessionId)) return user;
        }
        return null;
    }

    private User getUserByUserId(int userId) {
        for (User user : users) {
            if (user.getUserId() == userId) return user;
        }
        return null;
    }

    private boolean checkUserNameAlreadyExists(String username)
    {
        for(User u : users) {
            if (u.getUsername().equals(username)) return true;
        }
        return false;
    }

    private String getDate() {
        SimpleDateFormat formatter= new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        Date date = new Date(System.currentTimeMillis());
        return formatter.format(date);
    }

    private Square getSquare(int currentPlace) {
        for (Square square : squareList) {
            if (square.getSquareId() == currentPlace) return square;
        }
        return null;
    }

    private synchronized void switchTurn() { if (++playerTurn > users.size()) playerTurn = 1; }

    private synchronized boolean playersTurn() { return playerNr == playerTurn; }
}
