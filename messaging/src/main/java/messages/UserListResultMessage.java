package messages;

import models.User;

import java.util.List;

public class UserListResultMessage {

    private List<User> users;

    public UserListResultMessage(List<User> users) { this.users = users; }

    public List<User> getUsers() { return users; }
}
