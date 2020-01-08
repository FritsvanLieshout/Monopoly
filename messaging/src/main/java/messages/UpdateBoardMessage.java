package messages;

public class UpdateBoardMessage {

    private String sessionId;

    public UpdateBoardMessage(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getSessionId() { return sessionId; }
}
