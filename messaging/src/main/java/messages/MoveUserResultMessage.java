package messages;

import models.Board;
import models.User;

public class MoveUserResultMessage {

    private String sessionId;
    private int dice;

    public MoveUserResultMessage(int dice, String sessionId) {
        this.dice = dice;
        this.sessionId = sessionId;
    }

    public int getDice() { return dice; }

    public String getSessionId() { return sessionId; }
}
