package messages;

import java.util.List;

public class UsersInGameMessage {

    private List<String> usernameList;

    public UsersInGameMessage(List<String> usernameList) { this.usernameList = usernameList; }

    public List<String> getUsernameList() { return usernameList; }
}
