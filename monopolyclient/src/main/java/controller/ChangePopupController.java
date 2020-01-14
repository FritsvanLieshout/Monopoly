package controller;

import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;

public class ChangePopupController {

    public ImageView ivChange;
    public Label lblMessage;
    public Label lblUsername;

    public ChangePopupController(String username, String message) {
        Platform.runLater(() -> {
            lblUsername.setText(username);
            lblMessage.setText(message);
        });
    }
}
