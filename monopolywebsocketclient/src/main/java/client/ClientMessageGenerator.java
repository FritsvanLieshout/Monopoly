package client;

import client_interface.IClientMessageGenerator;
import messages.UserTestMessage;

public class ClientMessageGenerator implements IClientMessageGenerator {
    private ClientWebSocket client = ClientWebSocket.getInstance();

    public void sendPlayer(String username, String password) {
        client.send(new UserTestMessage(username, password));
    }
}
