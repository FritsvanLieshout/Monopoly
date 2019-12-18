package server;

import server_interface.IServerMessageGenerator;
import server_interface.IServerWebSocket;
import messages.UserTestResultMessage;

public class ServerMessageGenerator implements IServerMessageGenerator {
    private IServerWebSocket server = ServerWebSocket.getInstance();

    public void sendPlayer(String username) {
        server.broadcast(new UserTestResultMessage(username));
    }
}
