package client;

import client_interface.IClientMessageGenerator;
import client_interface.IClientWebSocket;
import messages.BuyFootballPlayerMessage;
import messages.LoginMessage;
import messages.MoveUserMessage;
import messages.RegisterUserMessage;
import messages.EndTurnMessage;

public class ClientMessageGenerator implements IClientMessageGenerator {

    private IClientWebSocket clientWebSocket;

    public ClientMessageGenerator(IClientWebSocket clientWebSocket) { this.clientWebSocket = clientWebSocket; }

    @Override
    public void registerUserOnServer(String username, String password) { clientWebSocket.send(new RegisterUserMessage(username, password)); }

    @Override
    public void login(String username, String password, boolean singlePlayer) { clientWebSocket.send(new LoginMessage(username, password, singlePlayer)); }

    @Override
    public void moveUser(int dice) { clientWebSocket.send(new MoveUserMessage(dice)); }

    @Override
    public void buyFootballPlayer() { clientWebSocket.send(new BuyFootballPlayerMessage()); }

    @Override
    public void endTurn(int playerTurn) { clientWebSocket.send(new EndTurnMessage(playerTurn)); }
}
