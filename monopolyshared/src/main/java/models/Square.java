package models;
import javafx.scene.layout.*;

public class Square extends StackPane {

    private int squareId;
    private String squareName;

    public Square() { }

    public Square(String squareName) {
        this.squareName = squareName;
    }

    public Square(int id, String squareName) {
        this.squareId = id;
        this.squareName = squareName;
    }

    public int getSquareId() { return squareId; }

    public void setSquareId(int id) { this.squareId = id; }

    public String getSquareName() { return squareName; }

    public void setSquareName(String name) { this.squareName = name; }
}
