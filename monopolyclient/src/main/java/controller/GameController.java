package controller;

import enums.Card;
import enums.Stadium;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.*;
import javafx.scene.shape.Circle;
import javafx.fxml.Initializable;
import models.GoalBonusSquare;
import models.Square;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class GameController implements Initializable {

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

    public GameController() {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        squareNames = new String[40];
        initSquareNames(squareNames);
        board = new Square[10][10];
        initBoard(board, gpMonopolyBoard);
    }

    private void initBoard(Square[][] board, GridPane grid) {
        for (int x = 0; x < 10; x++) {
            for (int y = 0; y < 10; y++) {
                for (int i = 0; i < 40; i++) {
                    if (x == 0 || y == 0 || x == 9 || y == 9) {

                        Square s = new Square();
                        board[x][y] = s;
                        s.setSquareName(squareNames[i]);
                        s.setStyle("-fx-border-color: black");
                        grid.add(s, x, y);
                    }
                }
            }
        }
    }

    private void initSquareNames(String[] names) {
        names[0] = "Start";
        names[1] = "Harry Kane";
        names[2] = Card.COMMUNITY_CHEST.toString();
        names[3] = "Heung-Min Son";
        names[4] = "TODO3";
        names[5] = Stadium.EMIRATES_STADIUM.toString();
        names[6] = "Lorenzo Insigne";
        names[7] = "Kalidou Koulibaly";
        names[8] = Card.CHANGE.toString();
        names[9] = "Dries Mertens";
        names[10] = "DressingRoom";
        names[11] = "Marcus Rashford";
        names[12] = "TODO1";
        names[13] = "David de Gea";
        names[14] = "Paul Pogba";
        names[15] = Stadium.SIGNAL_IDUNA_PARK.toString();
        names[16] = "Matthijs de Ligt";
        names[17] = "Paulo Dybala";
        names[18] = Card.COMMUNITY_CHEST.toString();
        names[19] = "Cristiano Ronaldo";
        names[20] = "Goal Bonus";
        names[21] = "Mohamed Salah";
        names[22] = Card.CHANGE.toString();
        names[23] = "Sadio Mané";
        names[24] = "Virgil van Dijk";
        names[25] = Stadium.CAMP_NOU.toString();
        names[26] = "Sergio Aguero";
        names[27] = "Raheem Sterling";
        names[28] = "TODO2";
        names[29] = "Kevin de Bruyne";
        names[30] = "Red Card!";
        names[31] = "Luis Suarez";
        names[32] = "Frenkie de Jong";
        names[33] = Card.COMMUNITY_CHEST.toString();
        names[34] = "Lionel Messi";
        names[35] = Stadium.PHILIPS_STADION.toString();
        names[36] = Card.CHANGE.toString();
        names[37] = "Kylian Mbappe";
        names[38] = "TODO4";
        names[39] = "Neymar";
    }



}
