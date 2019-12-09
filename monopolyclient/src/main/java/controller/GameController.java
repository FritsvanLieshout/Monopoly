package controller;

import game.IMonopolyGame;
import game.MonopolyGame;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.*;
import javafx.scene.shape.Circle;
import javafx.fxml.Initializable;
import logic_factory.LogicFactory;
import logic_interface.*;
import models.*;

import java.net.URL;
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

    private IMonopolyGame game;
    Dice dice = new Dice();

    private LogicFactory logicFactory;

    public GameController() {
        logicFactory = new LogicFactory();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        game = new MonopolyGame();

        btnThrowDice.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                moveUser();
            }
        });

        btnBuyPlayer.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {

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

            }
        });
    }

    private void moveUser() {
        IBoardLogic IBL = logicFactory.getIBoardLogic();

        int dice1 = IBL.getDice(dice);
        int dice2 = IBL.getDice(dice);
        int nofDice = dice1 + dice2;

        IBL.moveUser(1, nofDice);

        lblDice1.setText(Integer.toString(dice1));
        lblDice2.setText(Integer.toString(dice2));
    }
}
