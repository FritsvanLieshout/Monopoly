package mock;

import models.User;
import restshared.IMonopolyRestClient;

import java.util.ArrayList;
import java.util.List;

public class MonopolyRestClientMock implements IMonopolyRestClient {

    private User mockUser = new User(1, "1", "Kevin");
    private User mockUser2 = new User(2, "2", "Lisa");
    private User mockUser3 = new User(3, "3", "Sander");
    private User mockUser4 = new User(4, "4", "Bart");

    private List<User> mockUsers = new ArrayList<>();

    public MonopolyRestClientMock() {
        mockUser.setPassword("I_Hate_Testing");
        mockUser2.setPassword("I-Like");
        mockUser3.setPassword("IsOwned");
        mockUser4.setPassword("Lieshout");
        mockUsers.add(mockUser);
        mockUsers.add(mockUser2);
        mockUsers.add(mockUser3);
        mockUsers.add(mockUser4);
    }

    @Override
    public boolean checkUsername(String username) {
        for (User user : mockUsers) {
            if (username.equals(user.getUsername())) return false;
        }
        return true;
    }

    @Override
    public Object getUserByCredentials(String username, String password) {
        if (username.equals(mockUser.getUsername()) && password.equals(mockUser.getPassword())) return mockUser;
        else if (username.equals(mockUser2.getUsername()) && password.equals(mockUser2.getPassword())) return mockUser2;
        else if (username.equals(mockUser3.getUsername()) && password.equals(mockUser3.getPassword())) return mockUser3;
        else if (username.equals(mockUser4.getUsername()) && password.equals(mockUser4.getPassword())) return mockUser4;
        else return null;
    }

    @Override
    public Object registerUser(String username, String password) {
        if (checkUsername(username)){
            User newUser = new User(4, "4", username);
            return newUser;
        }
        else return null;
    }
}
