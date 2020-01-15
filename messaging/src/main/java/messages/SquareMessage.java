package messages;

import models.User;

public class SquareMessage {

    private User user;
    private String message;

    public SquareMessage(User user, String message) {
        this.user = user;
        this.message = message;
    }

    public User getUser() { return user; }

    public String getMessage() { return message; }
}
