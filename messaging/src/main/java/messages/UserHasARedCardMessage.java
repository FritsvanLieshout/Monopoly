package messages;

import models.User;

public class UserHasARedCardMessage {

    private User user;

    public UserHasARedCardMessage(User user) { this.user = user; }

    public User getUser() { return user; }
}
