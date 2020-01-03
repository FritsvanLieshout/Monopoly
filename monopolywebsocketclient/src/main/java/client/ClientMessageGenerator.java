package client;

import client_interface.IClientMessageGenerator;
import client_interface.IClientWebSocket;
import messages.LoginMessage;
import messages.RegisterUserMessage;

public class ClientMessageGenerator implements IClientMessageGenerator {

    private IClientWebSocket clientWebSocket;

    public ClientMessageGenerator(IClientWebSocket clientWebSocket) { this.clientWebSocket = clientWebSocket; }

    @Override
    public void registerUserOnServer(String username, String password) {
        clientWebSocket.send(new RegisterUserMessage(username, password));
    }

    @Override
    public void login(String username, String password) {
        clientWebSocket.send(new LoginMessage(username, password));
    }

}
