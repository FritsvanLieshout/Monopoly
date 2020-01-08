package messages;

import models.User;

public class UpdateCurrentUserMessage {

    private User user;
    private String sessionId;

    public UpdateCurrentUserMessage(User user, String sessionId) {
        this.user = user;
        this.sessionId = sessionId;
    }

    public User getUser() { return user; }

    public String getSessionId() { return sessionId; }
}
