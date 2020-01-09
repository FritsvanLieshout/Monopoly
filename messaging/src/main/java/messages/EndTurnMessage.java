package messages;

public class EndTurnMessage {

    private int playerTurn;

    public EndTurnMessage(int playerTurn) { this.playerTurn = playerTurn; }

    public int getPlayerTurn() { return playerTurn; }
}
