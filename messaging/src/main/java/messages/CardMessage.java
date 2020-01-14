package messages;

import models.User;

public class CardMessage {

    private User user;
    private String message;
    private boolean result;

    public CardMessage(User user, String message, boolean result) {
        this.user = user;
        this.message = message;
        this.result = result;
    }

    public User getUser() { return user; }

    public String getMessage() { return message; }

    public boolean isResult() { return result; }
}
