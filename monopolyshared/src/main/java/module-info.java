module monopolyshared {
    requires javafx.graphics;
    exports communicatorshared;
    opens communicatorshared;

    exports restshared;
    opens restshared;

    exports models;
    opens models;
    exports enums;
}