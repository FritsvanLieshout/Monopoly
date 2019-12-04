package controller;

import enums.Card;
import enums.ClubColor;
import enums.FootballPlayer;
import enums.Stadium;
import game.IMonopolyGame;
import game.MonopolyGame;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.fxml.Initializable;
import javafx.stage.Stage;
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

    private Square[][] board = null;
    private String[] squareNames = null;

    private int snCount = 0;
    private int id = 0;

    private IMonopolyGame game;
    Dice dice = new Dice();

    public GameController() {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        game = new MonopolyGame();
        squareNames = new String[40];
        initSquareNames(squareNames);
        board = new Square[10][10];
        initBoard(board, gpMonopolyBoard);

        btnThrowDice.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                moveUser();
            }
        });
    }

    private void initBoard(Square[][] board, GridPane grid) {
            for (int y = 0; y < 10; y++) {
                for (int x = 0; x < 10; x++) {
                if (x == 0 || y == 0 || x == 9 || y == 9) {
                    if (snCount <= squareNames.length) {
                        id++;
                        if (snCount == 0) {
                            Square s = new StartSquare(squareNames[snCount]);
                            board[x][y] = s;
                            s.setSquareId(id);
                            s.setStyle("-fx-border-color: black; -fx-pref-width: 75");
                            grid.add(s, x, y);
                            s.getChildren().add(new Label(squareNames[snCount]));
                        } else if (snCount == 9) {
                            Square s = new DressingRoomSquare(squareNames[snCount]);
                            board[x][y] = s;
                            s.setSquareId(id);
                            s.setStyle("-fx-border-color: black; -fx-pref-width: 75");
                            s.getChildren().add(new Label(squareNames[snCount]));
                            grid.add(s, x, y);
                        } else if (snCount == 35) {
                            Square s = new GoalBonusSquare(squareNames[snCount], 500);
                            board[x][y] = s;
                            s.setSquareId(id);
                            s.setStyle("-fx-border-color: black; -fx-pref-width: 75");
                            s.getChildren().add(new Label(squareNames[snCount]));
                            grid.add(s, x, y);
                        } else if (snCount == 26) {
                            Square s = new RedCardSquare(squareNames[snCount]);
                            board[x][y] = s;
                            s.setSquareId(id);
                            s.setStyle("-fx-border-color: black; -fx-pref-width: 75");
                            s.getChildren().add(new Label(squareNames[snCount]));
                            grid.add(s, x, y);
                        } else if (snCount == 2 || snCount == 7 || snCount == 12 || snCount == 20 || snCount == 23 || snCount == 33) {
                            Square s = new Square();
                            board[x][y] = s;
                            s.setSquareId(id);
                            s.setSquareName(squareNames[snCount]);
                            s.setStyle("-fx-border-color: black; -fx-pref-width: 75");
                            s.getChildren().add(new Label(squareNames[snCount]));
                            grid.add(s, x, y);
                        } else {
                            if (snCount == 4 || snCount == 16 || snCount == 17 || snCount == 30) {
                                Square s = new PlayerSquare(squareNames[snCount], 200);
                                board[x][y] = s;
                                s.setSquareId(id);
                                s.setSquareName(squareNames[snCount]);
                                s.setStyle("-fx-border-color: black; -fx-pref-width: 75");
                                s.getChildren().add(new Label(squareNames[snCount]));
                                grid.add(s, x, y);
                            } else {
                                Square s = new PlayerSquare(squareNames[snCount], 200 * snCount / 4);
                                board[x][y] = s;
                                s.setSquareId(id);
                                s.setSquareName(squareNames[snCount]);
                                s.setStyle("-fx-border-color: black; -fx-pref-width: 75");

                                Label lbl = new Label(squareNames[snCount]);
                                s.getChildren().add(lbl);
                                grid.add(s, x, y);
                            }
                        }
                        snCount++;
                    }
                }
            }
        }
    }

    private void initSquareNames(String[] names) {
        names[0] = "Start";
        names[1] = FootballPlayer.KANE.toString();
        names[2] = Card.COMMUNITY_CHEST.toString();
        names[3] = FootballPlayer.SON.toString();
        names[4] = Stadium.EMIRATES_STADIUM.toString();
        names[5] = FootballPlayer.INSIGNE.toString();
        names[6] = FootballPlayer.KOULIBALY.toString();
        names[7] = Card.CHANGE.toString();
        names[8] = FootballPlayer.MERTENS.toString();
        names[9] = "DressingRoom";
        names[10] = FootballPlayer.NEYMAR.toString();
        names[11] = FootballPlayer.RASHFORD.toString();
        names[12] = Card.CHANGE.toString();
        names[13] = FootballPlayer.DE_GEA.toString();
        names[14] = FootballPlayer.MBAPPE.toString();
        names[15] = FootballPlayer.POGBA.toString();
        names[16] = Stadium.PHILIPS_STADION.toString();
        names[17] = Stadium.SIGNAL_IDUNA_PARK.toString();
        names[18] = FootballPlayer.MESSI.toString();
        names[19] = FootballPlayer.DE_LIGT.toString();
        names[20] = Card.COMMUNITY_CHEST.toString();
        names[21] = FootballPlayer.DYBALA.toString();
        names[22] = FootballPlayer.DE_JONG.toString();
        names[23] = Card.COMMUNITY_CHEST.toString();
        names[24] = FootballPlayer.SUAREZ.toString();
        names[25] = FootballPlayer.RONALDO.toString();
        names[26] = "Red Card";
        names[27] = FootballPlayer.DE_BRUYNE.toString();
        names[28] = FootballPlayer.STERLING.toString();
        names[29] = FootballPlayer.AGUERO.toString();
        names[30] = Stadium.CAMP_NOU.toString();
        names[31] = FootballPlayer.VAN_DIJK.toString();
        names[32] = FootballPlayer.MANÉ.toString();
        names[33] = Card.CHANGE.toString();
        names[34] = FootballPlayer.SALAH.toString();
        names[35] = "Goal Bonus!";
    }

    private void moveUser() {
        int dice1 = dice.getNofDice();
        int dice2 = dice.getNofDice();

        lblDice1.setText(Integer.toString(dice1));
        lblDice2.setText(Integer.toString(dice2));
        game.moveUser(1, dice1, dice2);
    }

    private void startGame() {

    }
}
