package client;

import messages.UserTestMessage;

public class ClientMessageGenerator {
    private ClientWebSocket client = ClientWebSocket.getInstance();

    public void sendPlayer(String username, String password) {
        client.send(new UserTestMessage(username, password));
    }
}
