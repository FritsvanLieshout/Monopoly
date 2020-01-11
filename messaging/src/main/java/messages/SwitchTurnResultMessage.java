package messages;

public class SwitchTurnResultMessage {

    private int playerTurn;
    private String sessionId;

    public SwitchTurnResultMessage(int playerTurn, String sessionId) {
        this.playerTurn = playerTurn;
        this.sessionId = sessionId;
    }

    public int getPlayerTurn() { return playerTurn; }

    public String getSessionId() { return sessionId; }
}
