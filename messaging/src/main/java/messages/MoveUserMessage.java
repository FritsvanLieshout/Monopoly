package messages;

public class MoveUserMessage {

    private int dice;
    private String sessionId;

    public MoveUserMessage(int dice) { this.dice = dice; }

    public MoveUserMessage(int dice, String sessionId) {
        this.dice = dice;
        this.sessionId = sessionId;
    }

    public int getDice() { return dice; }

    public String getSessionId() { return sessionId; }
}
