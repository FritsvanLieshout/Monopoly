package messages;

import models.User;

public class BrokeMessage {

    private User user;

    public BrokeMessage(User user) { this.user = user; }

    public User getUser() { return user; }
}
