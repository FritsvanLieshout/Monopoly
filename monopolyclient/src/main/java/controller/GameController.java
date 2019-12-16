package controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.*;
import javafx.scene.shape.Circle;
import javafx.fxml.Initializable;
import logic.BoardLogic;
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

    Dice dice = new Dice();

    private LogicFactory logicFactory;
    private IBoardLogic iBoardLogic;
    private IGameLogic iGameLogic;

    private User user;
    private Board board;

    public GameController() {
        logicFactory = new LogicFactory();
        iBoardLogic = logicFactory.getIBoardLogic();
        iGameLogic = logicFactory.getIGameLogic();
        board = iBoardLogic.getBoard();
        user = new User(1, "Kevin"); //TODO: this need the user that's logged in
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

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

    private void moveUser() {
        int dice1 = iGameLogic.getDice(dice);
        int dice2 = iGameLogic.getDice(dice);
        int noDice = dice1 + dice2;

        iGameLogic.moveUser(user, board, noDice);

        lblDice1.setText(Integer.toString(dice1));
        lblDice2.setText(Integer.toString(dice2));

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
