package gui;

import client.ClientMessageGenerator;
import client.ClientMessageProcessor;
import client.ClientWebSocket;
import client_interface.*;
import controller.GameController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import messaging.ClientHandlerFactory;
import socketcommunication.GameClient;


public class GameMain extends Application {

    IGameClient gameClient;
    Stage primaryStage;

    @Override
    public void start(Stage primaryStage) throws Exception {

        IClientWebSocket socket = new ClientWebSocket();
        IClientMessageGenerator generator = new ClientMessageGenerator(socket);
        gameClient = new GameClient(generator);
        IClientHandlerFactory factory = new ClientHandlerFactory();
        IClientMessageProcessor processor = new ClientMessageProcessor(factory);
        socket.setMessageProcessor(processor);
        socket.start();
        processor.registerGameClient(gameClient);
        loadFxml("/MonopolyGame.fxml", gameClient, primaryStage);
    }

    public void loadFxml(String fxml, IGameClient client, Stage primaryStage) throws Exception {
        if (this.gameClient == null) { this.gameClient = client; }
        if (this.primaryStage == null) { this.primaryStage = primaryStage; }
        loadFxml(fxml);
    }

    public void loadFxml(String fxml) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxml));
        loader.setControllerFactory(c -> {
            return new GameController(gameClient);
        });

        Parent root = loader.load();
        this.primaryStage.setTitle("Monopoly Game");
        this.primaryStage.setScene(new Scene(root, 1400, 800));
        this.primaryStage.show();

    }

    public static void main(String[] args) {
        launch(args);
    }
}
