module monopolyclient {
    requires javafx.graphics;
    requires javafx.controls;
    requires monopolyshared;
    requires javafx.fxml;

    exports gui;
    opens gui;
    exports controller;
    opens controller;
}