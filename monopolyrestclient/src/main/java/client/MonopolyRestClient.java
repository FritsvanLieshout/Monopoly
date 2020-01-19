package client;

import models.User;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import restshared.IMonopolyRestClient;


public class MonopolyRestClient implements IMonopolyRestClient {

    private static final String URL = "http://localhost:8049/monopoly/";

    private RestTemplate restTemplate;
    private HttpHeaders httpHeaders;

    public MonopolyRestClient() {
        this.restTemplate = new RestTemplate();
        this.httpHeaders = new HttpHeaders();
        httpHeaders.set("Accept", MediaType.APPLICATION_JSON_VALUE);
    }

    @Override
    public boolean checkUsername(String username) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(URL + "checkUsername?")
                .queryParam("username", username);

        HttpEntity<?> entity = new HttpEntity<>(httpHeaders);
        ResponseEntity<Boolean> response = restTemplate.exchange(builder.toUriString(), HttpMethod.GET, entity, Boolean.class);
        return response.getBody();
    }

    @Override
    public Object getUserByCredentials(String username, String password) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(URL + "userCred?")
                .queryParam("username", username)
                .queryParam("password", password);

        HttpEntity<?> entity = new HttpEntity<>(httpHeaders);
        ResponseEntity<User> response = restTemplate.exchange(builder.toUriString(), HttpMethod.GET, entity, User.class);
        return response.getBody();
    }

    @Override
    public Object registerUser(String username, String password) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(URL + "register?")
                .queryParam("username", username)
                .queryParam("password", password);

        HttpEntity<?> entity = new HttpEntity<>(httpHeaders);
        ResponseEntity<User> response = restTemplate.exchange(builder.toUriString(), HttpMethod.POST, entity, User.class);
        return response.getBody();
    }
}
