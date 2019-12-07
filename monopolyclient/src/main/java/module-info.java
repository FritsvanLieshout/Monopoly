module monopolyclient {
    requires javafx.graphics;
    requires javafx.controls;
    requires monopolyshared;
    requires javafx.fxml;
    requires factory;
    requires logicinterface;

    exports gui;
    opens gui;
    exports controller;
    opens controller;
}