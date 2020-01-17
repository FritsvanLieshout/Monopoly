package restshared;

public interface IMonopolyRestClient {
    boolean checkUsername(String username);
    Object getUserByCredentials(String username, String password);
    Object registerUser(String username, String password);
}
