package restshared;

import java.util.List;

public class MonopolyResponse {

    // Indicates whether REST call was successful
    private boolean success;

    // List of users
    private List<UserDto> users;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public List<UserDto> getUsers() {
        return users;
    }

    public void setUsers(List<UserDto> users) {
        this.users = users;
    }
}
