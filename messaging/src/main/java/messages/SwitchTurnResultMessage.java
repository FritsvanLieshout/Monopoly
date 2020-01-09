package messages;

public class SwitchTurnResultMessage {

    private int playerTurn;

    public SwitchTurnResultMessage(int playerTurn) { this.playerTurn = playerTurn; }

    public int getPlayerTurn() { return playerTurn; }
}
