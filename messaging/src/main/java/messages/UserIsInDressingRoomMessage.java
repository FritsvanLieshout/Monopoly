package messages;

import models.User;

public class UserIsInDressingRoomMessage {

    private User user;

    public UserIsInDressingRoomMessage(User user) { this.user = user; }

    public User getUser() { return user; }
}
