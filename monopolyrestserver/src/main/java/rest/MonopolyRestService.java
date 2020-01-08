package rest;

import monopoly.Monopoly;
import monopoly.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import restshared.UserDto;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/monopoly")
public class MonopolyRestService {

    private static final Logger log = LoggerFactory.getLogger(MonopolyRestService.class);

    public MonopolyRestService() { }

    /**
     * Find user by id.
     * @param userIdAsString user id (as string)
     * @return the player with given id
     */
    @GET
    @Path("/user/{userId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPlayer(@PathParam("userId") String userIdAsString) {
        log.info("[Server getUser]");

        //Find user
        int userId = Integer.parseInt(userIdAsString);
        User userFromMonopoly = Monopoly.getInstance().getUser(userId);

        //Check if the user exist
        if (userFromMonopoly == null) {
            //Client error 400 - Bad Request
            return Response.status(400).entity(RestResponseHelper.getErrorResponseString()).build();
        }

        //Define response
        return Response.status(200).entity(RestResponseHelper.getSingleUserResponse(userFromMonopoly)).build();
    }

    /**
     * Get all users in monopoly game.
     * @return all users
     */
    @GET
    @Path("/user/all")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllUsers() {

        log.info("[Server getAllUsers]");

        // Get all users from monopoly game
        List<User> allUserFromMonopoly = Monopoly.getInstance().getAllUsers();

        // Define response
        return Response.status(200).entity(RestResponseHelper.getAllUsersResponse(RestResponseHelper.getUserDtoList(allUserFromMonopoly))).build();
    }

    /**
     * Register a new user to monopoly game
     * @param userRequest data of new user
     * @return register a new user
     */
    @POST
    @Path("/register")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response registerUser(UserDto userRequest) {

        log.info("[Server registerUser]");

        //Check request
        if (userRequest == null) {
            //Client error 400 - Bad Request
            return Response.status(400).entity(RestResponseHelper.getErrorResponseString()).build();
        }

        //Register user to monopoly game
        String username = userRequest.getUsername();
        String password = userRequest.getPassword();
        User newUser = Monopoly.getInstance().registerUser(username, password);

        // Define response
        return Response.status(200).entity(RestResponseHelper.getSingleUserResponse(newUser)).build();
    }

    /**
     * user login for monopoly game
     * @param userRequest data of user
     * @return logged in user
     */
    @POST
    @Path("/login")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response loginUser(UserDto userRequest) {
        log.info("[Server loginUser]");

        //Check request
        if (userRequest == null) {
            //Client error 400 - Bad Request
            return Response.status(400).entity(RestResponseHelper.getErrorResponseString()).build();
        }

        int userId = userRequest.getUserId();
        String username = userRequest.getUsername();
        String password = userRequest.getPassword();
        User user = Monopoly.getInstance().getUserByNameAndPassword(userId, username, password);

        //Define response
        return Response.status(200).entity(RestResponseHelper.getSingleUserResponse(user)).build();
    }
}
