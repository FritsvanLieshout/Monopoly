package restshared;

import java.util.List;

public class MonopolyResponse {

    // Indicates whether REST call was successful
    private boolean success;

    // List of users
    private List<UserDTO> users;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public List<UserDTO> getUsers() {
        return users;
    }

    public void setPlayers(List<UserDTO> users) {
        this.users = users;
    }
}
