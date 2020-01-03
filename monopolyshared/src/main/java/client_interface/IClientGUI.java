package client_interface;

public interface IClientGUI {

    void moveUser();

    void processRegistrationResponse(boolean response);

    void processUserRegistered(String username);

    void processLoginResponse(String token);
}
