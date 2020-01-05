package client_interface;

import models.User;

import java.util.List;

public interface IClientGUI {

    /**
     *
     */
    void moveUser();

    /**
     *
     * @param response
     */
    void processRegistrationResponse(boolean response);

    /**
     *
     * @param username
     */
    void processUserRegistered(String username);

    /**
     *
     * @param token
     */
    void processLoginResponse(String token);

    /**
     *
     * @param dice
     * @param sessionId
     */
    void processMoveUserResponse(int dice, String sessionId);

    /**
     *
     * @param usernameList
     */
    void processUsersInGameResponse(List<String> usernameList);

    /**
     *
     * @param users
     */
    void processUserListResponse(List<User> users);

    /**
     *
     */
    void processStartGameResponse();

    /**
     *
     */
    void processUpdateUser(int currentPlace, String sessionId);
}
