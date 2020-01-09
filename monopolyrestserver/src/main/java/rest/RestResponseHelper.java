package rest;

import com.google.gson.Gson;
import monopoly.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import restshared.MonopolyResponse;
import restshared.UserDto;

import java.util.ArrayList;
import java.util.List;

public class RestResponseHelper {

    private static final Logger log = LoggerFactory.getLogger(RestResponseHelper.class);
    private static final Gson gson = new Gson();
    static final String serverResponse = "[Server response] ";

    static String getErrorResponseString()
    {
        MonopolyResponse response = new MonopolyResponse();
        response.setSuccess(false);
        String output = gson.toJson(response);
        log.info(serverResponse + output);
        return output;
    }

    static String getSingleUserResponse(User userFromMonopoly)
    {
        MonopolyResponse response = new MonopolyResponse();
        response.setSuccess(true);
        List<UserDto> users = new ArrayList<>();
        UserDto player = userFromMonopoly.createDto();
        users.add(player);
        response.setUsers(users);
        String output = gson.toJson(response);
        log.info(serverResponse + output);
        return output;
    }

    static String getSuccessResponse(boolean success)
    {
        MonopolyResponse response = new MonopolyResponse();
        response.setSuccess(success);
        String output = gson.toJson(response);
        log.info(serverResponse + output);
        return output;
    }

    static String getAllUsersResponse(List<UserDto> allUsers)
    {
        MonopolyResponse response = new MonopolyResponse();
        response.setSuccess(true);
        response.setUsers(allUsers);
        String output = gson.toJson(response);
        log.info(serverResponse + output);
        return output;
    }

    static List<UserDto> getUserDtoList(List<User> allUsersFromSeaBattle)
    {
        List<UserDto> allUsers = new ArrayList<>();
        for (User u : allUsersFromSeaBattle) {
            UserDto player = u.createDto();
            allUsers.add(player);
        }
        return allUsers;
    }

}
