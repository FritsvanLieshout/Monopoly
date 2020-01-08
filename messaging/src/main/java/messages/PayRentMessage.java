package messages;

import models.User;

public class PayRentMessage {

    private User user;

    public PayRentMessage(User user) {
        this.user = user;
    }

    public User getUser() { return user; }
}
