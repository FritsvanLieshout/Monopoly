package client_interface;

import java.util.List;

public interface IClientGUI {

    void moveUser();

    void processRegistrationResponse(boolean response);

    void processUserRegistered(String username);

    void processLoginResponse(String token);

    void processMoveUserResponse(int dice, String sessionId);

    void processUsersInGameResponse(List<String> usernameList);
}
