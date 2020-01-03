package messages;

import java.util.List;

public class UsersInGameResultMessage {

    private List<String> usernameList;

    public UsersInGameResultMessage(List<String> usernameList) { this.usernameList = usernameList; }

    public List<String> getUsernameList() { return usernameList; }
}
