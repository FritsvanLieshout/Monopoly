package controller;

import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;

public class CommunityChestPopupController {

    public ImageView ivCommunityChest;
    public Label lblMessage;
    public Label lblUsername;

    public CommunityChestPopupController(String username, String message) {
        Platform.runLater(() -> {
            lblUsername.setText(username);
            lblMessage.setText(message); 
        });
    }
}
