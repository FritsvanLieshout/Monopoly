package models;

public abstract class Square {

    private String squareName;

    public Square(String squareName) {
        this.squareName = squareName;
    }

    public String getSquareName() {
        return squareName;
    }

    public void setSquareName(String name) { this.squareName = name; }

    public abstract void action(User user, Board board);
}
