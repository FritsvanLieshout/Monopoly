package models;
import javafx.scene.layout.StackPane;

public class Square extends StackPane {

    private int squareId;
    private String squareName;
    private int price;
    private int owner = -1;
    private int rentPrice;

    public Square() {}

    public Square(String squareName) { this.squareName = squareName; }

    public Square(String squareName, int price, int owner, int rentPrice) {
        this.squareName = squareName;
        this.price = price;
        this.owner = owner;
        this.rentPrice = rentPrice;
    }

    public int getSquareId() { return squareId; }

    public void setSquareId(int id) { this.squareId = id; }

    public String getSquareName() { return squareName; }

    public void setSquareName(String name) { this.squareName = name; }

    public int getPrice() { return price; }

    public void setPrice(int price) { this.price = price; }

    public int getOwner() { return owner; }

    public void setOwner(int owner) { this.owner = owner; }

    public int getRentPrice() { return rentPrice; }

    public void setRentPrice(int rentPrice) { this.rentPrice = rentPrice; }
}
