package monopoly;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Monopoly {

    //The users are stored in a map with key userId and value User-object
    private final Map<Integer, User> users;

    //Id for the next user to be added to the game
    private int nextUserId;

    //Instance of sea battle (singleton pattern)
    private static Monopoly instance;

    // Singleton pattern
    private Monopoly() {
        users = new HashMap<>();
        nextUserId = 1;
    }

    /**
     * Get singleton instance of monopoly
     * @return instance of monopoly
     */
    public static Monopoly getInstance() {
        if (instance == null) {
            instance = new Monopoly();
        }
        return instance;
    }

    /**
     * Get user with given id.
     * @param userId
     * @return user with given id.
     */
    public User getUser(int userId) {
        return users.get(userId);
    }

    /**
     * Get user with given username and password.
     * @param userId
     * @param username
     * @param password
     * @return user with the given username and password.
     */
    public User getUserByNameAndPassword(int userId, String username, String password) {
        if (users.get(userId).getUsername().equals(username) && users.get(userId).getPassword().equals(password)) {
            return users.get(userId);
        }
        return users.get(-1);
    }

    /**
     * Get all users in monopoly game.
     * @return all users in monopoly game
     */
    public List<User> getAllUsers() {
        return new ArrayList<>(users.values());
    }

    /**
     * Register a new user to the game.
     * @param username name of the new user
     * @param password password of the password
     * @return new user
     */
    public User registerUser(String username, String password) {
        // Define number for new user
        int userId = nextUserId;
        nextUserId++;

        //Create new user
        User user = new User(userId, username, password);

        //Put new user in the monopoly game
        users.put(userId, user);

        //Return new user
        return user;
    }
}
