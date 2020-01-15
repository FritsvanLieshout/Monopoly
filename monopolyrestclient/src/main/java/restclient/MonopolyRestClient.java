package restclient;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import restshared.MonopolyResponse;
import restshared.UserDto;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.logging.Level;

public class MonopolyRestClient {

    private static final Logger log = LoggerFactory.getLogger(MonopolyRestClient.class);

    private static final String url = "http://localhost:8049/monopoly";
    private final Gson gson = new Gson();
    private static final int NOTDEFINED = -1;

    /**
     * Get user with given user id.
     * @param userId user id
     * @return user with given id
     */
    public UserDto getUser(int userId) {
        String queryGet = "/user/" + userId;
        MonopolyResponse response = executeQueryGet(queryGet);
        return response.getUsers().get(0);
    }

    /**
     * Get all users in monopoly game.
     * @return all users
     */
    public List<UserDto> getAllUsers() {
        String queryGet = "/user/all";
        MonopolyResponse response = executeQueryGet(queryGet);
        return response.getUsers();
    }

    /**
     * Register a new user to the monopoly game.
     * @param username name of new user
     * @param password password of new user
     * @return new user
     */
    public UserDto registerUser(String username, String password) {
        UserDto userRequest = new UserDto(NOTDEFINED, username, password);
        String queryPost = "/register";
        MonopolyResponse response = executeQueryPost(userRequest, queryPost);
        return response.getUsers().get(0);
    }

    public UserDto loginUser(String username, String password) {
        UserDto userRequest = new UserDto(NOTDEFINED, username, password);
        String queryPost = "/login";
        MonopolyResponse response = executeQueryPost(userRequest, queryPost);
        return response.getUsers().get(0);
    }

    private MonopolyResponse executeQueryGet(String queryGet) {

        // Build the query for the REST service
        final String query = url + queryGet;
        log.info("[Query Get] : " + query);

        // Execute the HTTP GET request
        HttpGet httpGet = new HttpGet(query);
        return executeHttpUriRequest(httpGet);
    }

    private MonopolyResponse executeQueryPost(UserDto userRequest, String queryPost) {

        // Build the query for the REST service
        final String query = url + queryPost;
        log.info("[Query Post] : " + query);

        // Execute the HTTP POST request
        HttpPost httpPost = new HttpPost(query);
        httpPost.addHeader("content-type", "application/json");
        StringEntity params;

        try {
            params = new StringEntity(gson.toJson(userRequest));
            httpPost.setEntity(params);
        }

        catch (UnsupportedEncodingException ex) {
            java.util.logging.Logger.getLogger(MonopolyRestClient.class.getName()).log(Level.SEVERE, null, ex);
        }

        return executeHttpUriRequest(httpPost);
    }

    private MonopolyResponse executeHttpUriRequest(HttpUriRequest httpUriRequest) {

        // Execute the HttpUriRequest
        try (CloseableHttpClient httpClient = HttpClients.createDefault();
             CloseableHttpResponse response = httpClient.execute(httpUriRequest)) {
            log.info("[Status Line] : " + response.getStatusLine());
            HttpEntity entity = response.getEntity();
            final String entityString = EntityUtils.toString(entity);
            log.info("[Entity] : " + entityString);
            MonopolyResponse monopolyResponse = gson.fromJson(entityString, MonopolyResponse.class);

            return monopolyResponse;
        }

        catch (IOException e) {
            log.info("IOException : " + e.toString());
            MonopolyResponse monopolyResponse = new MonopolyResponse();
            monopolyResponse.setSuccess(false);

            return monopolyResponse;
        }

        catch (JsonSyntaxException e) {
            log.info("JsonSyntaxException : " + e.toString());
            MonopolyResponse monopolyResponse = new MonopolyResponse();
            monopolyResponse.setSuccess(false);

            return monopolyResponse;
        }
    }
}
