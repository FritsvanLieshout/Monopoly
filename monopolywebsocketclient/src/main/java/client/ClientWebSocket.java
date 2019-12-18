package client;

import client_interface.IClientWebSocket;
import messages.SocketMessage;
import messages.SocketMessageGenerator;
import serialization.Serializer;

import javax.websocket.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

@ClientEndpoint
public class ClientWebSocket implements IClientWebSocket {

    private String uri = "ws://localhost:8050/monopoly/";

    private Session session;

    private static ClientWebSocket instance = null;

    boolean isRunning = false;

    public static ClientWebSocket getInstance() {
        if (instance == null) {
            instance = new ClientWebSocket();
        }
        return instance;
    }

    @Override
    public void start() {
        System.out.println("[WebSocket Client start connection]");
        if (!isRunning) {
            startClient();
            isRunning = true;
        }
    }

    @Override
    public void stop() {
        System.out.println("[WebSocket Client stop]");
        if (isRunning) {
            stopClient();
            isRunning = false;
        }
    }

    /**
     * Start a WebSocket client.
     */
    private void startClient() {
        System.out.println("[WebSocket Client start]");
        try {
            WebSocketContainer container = ContainerProvider.getWebSocketContainer();
            container.connectToServer(this, new URI(uri));

        } catch (IOException | URISyntaxException | DeploymentException ex) {
            // do something useful eventually
            ex.printStackTrace();
        }
    }

    /**
     * Stop the client when it is running.
     */
    private void stopClient(){
        System.out.println("[WebSocket Client stop]");
        try {
            session.close();
        } catch (IOException ex){
            // do something useful eventually
            ex.printStackTrace();
        }
    }

    @OnOpen
    public void onWebSocketConnect(Session session){
        this.session = session;
    }

    @OnMessage
    public void onWebSocketText(String message, Session session){
        onWebSocketMessageReceived(message, session.getId());
    }

    public void onWebSocketMessageReceived(String message, String sessionId)
    {
        Serializer ser = Serializer.getSerializer();
        SocketMessage msg = ser.deserialize(message, SocketMessage.class);
        ClientMessageProcessor messageProcessor = new ClientMessageProcessor();
        messageProcessor.processMessage(sessionId, msg.getMessageType(), msg.getMessageData());
    }

/*    private IMessageProcessor messageProcessor;

    @Override
    public void setMessageProcessor(IMessageProcessor handler) {
        this.messageProcessor = handler;
    }*/

    @OnError
    public void onWebSocketError(Session session, Throwable cause) {
        System.out.println("[WebSocket Client connection error] " + cause.toString());
    }

    @OnClose
    public void onWebSocketClose(CloseReason reason){
        System.out.print("[WebSocket Client close session] " + session.getRequestURI());
        System.out.println(" for reason " + reason);
        session = null;
    }

    private void sendMessageToServer(String message)
    {
        try {
            session.getBasicRemote().sendText(message);
        } catch (IOException ex) {
            System.out.print("[WebSocket Client couldn't send to server] " + session.getRequestURI());
        }
    }

    public void send(Object object)
    {
        SocketMessageGenerator generator = new SocketMessageGenerator();
        String msg = generator.generateMessageString(object);
        sendMessageToServer(msg);
    }
}
