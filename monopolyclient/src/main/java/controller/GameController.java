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

    private LogicFactory logicFactory;
    private IBoardLogic iBoardLogic;
    private IGameLogic iGameLogic;

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
            }
        });
    }

    //TODO -> This method checks if there are 2 to 4 user in the game. And set al the labels for the users in the game = in game logic
    private void startGame() { }

    @Override
    public void moveUser() {
        int dice1 = iGameLogic.getDice(dice);
        int dice2 = iGameLogic.getDice(dice);
        int noDice = dice1 + dice2;

        lblDice1.setText(Integer.toString(dice1));
        lblDice2.setText(Integer.toString(dice2));

        //getGameClient().moveUser(noDice);
        getGameClient().moveUser(8);
    }

    @Override
    public void processRegistrationResponse(boolean response) {
        Platform.runLater(() -> {
            if (response) {
                showAlert("Monopoly", "Registration success!");
            }
            else {
                showAlert("Monopoly", "Registration failed, please try again!");
            }
        });
    }

    @Override
    public void processUserRegistered(String username) {
        //showAlert("Monopoly", "User: " + username + " has been registered");
        lvLog.getItems().add(getDate() + ": " +  username + " has been registered!");
    }

    @Override
    public void processLoginResponse(String token) {
        Platform.runLater(() -> {
            if (token == null || token.equals("")) {
                showAlert("Monopoly", "Login Failed");
            }
            else {
                showAlert("Monopoly", "Login Success, waiting for other opponent(s)");
                btnLogin.setDisable(true);
                btnRegister.setDisable(true);
                tfUsername.setDisable(true);
                tfPassword.setDisable(true);
            }
        });
    }

    @Override
    public void processMoveUserResponse(int dice, String sessionId) {
        Platform.runLater(() -> {
            User currentUser = getUser(sessionId);
            movePawnOnBoard(currentUser.getCurrentPlace(), currentUser.getUserId());
            String msg = currentUser.getUsername() + " has dice " + dice + " and goes to " + board.getSquares()[currentUser.getCurrentPlace()].getSquareName();
            //showAlert("Monopoly", msg, sessionId);
            lvLog.getItems().add(getDate() + ": " + msg);
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
            setDisable(false);
        });
    }

    @Override
    public void processUpdateUser(User user, String sessionId) {
        Platform.runLater(() -> {
            User currentUser = getUser(sessionId);
            currentUser.setPlace(user.getCurrentPlace());
            currentUser.setInDressingRoom(user.isInDressingRoom());
            currentUser.setWallet(user.getWallet());
            //showAlert(user.getUsername(), user.getUsername() + " 's wallet has been updated -> € "+ user.getWallet().getMoney());
            lvLog.getItems().add(getDate() + ": " + user.getUsername() + ", position on board: " + user.getCurrentPlace() + ", his/her has been updated -> €" + user.getWallet().getMoney());
        });
    }

    @Override
    public void processUpdateBoardResponse(String sessionId) {
        Platform.runLater(() -> {
            User currentUser = getUser(sessionId);
            for (Square s : squareList) {
                if (s.getSquareId() == currentUser.getCurrentPlace()) {
                    s.setOwner(currentUser.getUserId());
                    setOwnerOfSquareColor(currentUser.getCurrentPlace(), currentUser.getUserId());
                    lvLog.getItems().add(getDate() + ": " + s.getSquareName() + " has been added to " + currentUser.getUsername() + "'s club!");
                }
            }
        });
    }

    @Override
    public void processNonValueSquareResponse() {
        Platform.runLater(() -> {
            showAlert("Monopoly", "The square where you became is a non value square");
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
            for (Square s : squareList) {
                if (s.getSquareId() == currentUser.getCurrentPlace()) {
                    lvLog.getItems().add(getDate() + ": " + currentUser.getUsername() + " has to pay €" + s.getRentPrice() + " rent to " + ownedUser.getUsername());
                    User newOwnedUser = getUser(ownedUser.getSessionId());
                    newOwnedUser.setWallet(ownedUser.getWallet());
                    lvLog.getItems().add(getDate() + ": " + newOwnedUser.getUsername() + ", your wallet has been updated -> €" + newOwnedUser.getWallet().getMoney());
                    User newCurrentUser = getUser(currentUser.getSessionId());
                    newCurrentUser.setWallet(currentUser.getWallet());
                    lvLog.getItems().add(getDate() + ": " + newCurrentUser.getUsername() + ", your wallet has been updated -> €" + newCurrentUser.getWallet().getMoney());
                    break;
                }
            }
        });
    }

    private void showAlert(String header, String content, String sessionId)
    {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
               // User currentUser = getUser(sessionId);
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle(header);
               // alert.setHeaderText("Message for " + currentUser.getUsername());
                alert.setContentText(content);
                alert.showAndWait();
            }
        });
    }

    private void showAlert(String header, String content)
    {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle(header);
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

        if(username.equals("") || username == null)
        {
            showAlert("Monopoly", "Invalid username");
        }
        else if (password.equals("") || password == null) {
            showAlert("Monopoly", "Invalid password");
        }
        else {
            if (!isLogin) {
                getGameClient().registerUser(username, password);
            } else {
                getGameClient().loginUser(username, password);
            }
        }
    }

    //TODO -> this method in game logic, if the user became here the other user will be notified and his new position (10) will be set
    private boolean varCheckForARedCard(User user) {
        if (user.getCurrentPlace() == 30) {
            iGameLogic.redCard(user); //TODO -> getGameClient().redCard(user)
            movePawnOnBoard(10, user.getUserId());  // -> squareId 10 = Dressing Room
            return true;
        }
        return false;
    }

    private void buyFootballPlayer() {
        getGameClient().buyFootballPlayer();
    }

    private void endTurn() {
        //iGameLogic.switchTurn(board, users); //TODO -> to server
    }

    private void setDisable(boolean disable) {
        btnThrowDice.setDisable(disable);
        btnEndTurn.setDisable(disable);
        btnBuyPlayer.setDisable(disable);
        btnPlaceHotel.setDisable(disable);
        btnPlaceHouse.setDisable(disable);
    }

    private User getUser(String sessionId) {
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
            if (u.getUsername().equals(username)) {
                return true;
            }
        }
        return false;
    }

    private String getDate() {
        SimpleDateFormat formatter= new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        Date date = new Date(System.currentTimeMillis());
        return formatter.format(date);
    }
    //ArrayList met Change and Community Chests -> Add Money, Withdraw Money and Go to a specific square
}
