package messages;

import models.User;

public class CardMessage {

    private User user;
    private String message;

    public CardMessage(User user, String message) {
        this.user = user;
        this.message = message;
    }

    public User getUser() { return user; }

    public String getMessage() { return message; }
}
