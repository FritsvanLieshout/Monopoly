package server;

import messages.UserTestResultMessage;

public class ServerMessageGenerator {
    private IServerWebSocket server = ServerWebSocket.getInstance();

    public void sendPlayer(String username) {
        server.broadcast(new UserTestResultMessage(username));
    }
}
