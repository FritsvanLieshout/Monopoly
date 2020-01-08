package messages;

import models.User;

public class PayRentMessage {

    private User currentUser;
    private User ownedUser;

    public PayRentMessage(User currentUser, User ownedUser) {
        this.currentUser = currentUser;
        this.ownedUser = ownedUser;
    }

    public User getCurrentUser() { return currentUser; }

    public User getOwnedUser() { return ownedUser; }
}
