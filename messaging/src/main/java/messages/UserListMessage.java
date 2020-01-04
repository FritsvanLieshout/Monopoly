package messages;

import models.User;

import java.util.List;

public class UserListMessage {

    private List<User> users;

    public UserListMessage(List<User> users) { this.users = users; }

    public List<User> getUsers() { return users; }
}
