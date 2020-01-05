package messages;

public class UpdatePlaceOfCurrentUserMessage {

    private int currentPlace;
    private String sessionId;

    public UpdatePlaceOfCurrentUserMessage(int currentPlace, String sessionId) {
        this.currentPlace = currentPlace;
        this.sessionId = sessionId;
    }

    public int getCurrentPlace() { return currentPlace; }

    public String getSessionId() { return sessionId; }
}
