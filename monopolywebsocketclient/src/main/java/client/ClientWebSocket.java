package client;

import client_interface.IClientMessageProcessor;
import client_interface.IClientWebSocket;
import messages.SocketMessage;
import messages.SocketMessageGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import serialization.Serializer;

import javax.websocket.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

@ClientEndpoint
public class ClientWebSocket implements IClientWebSocket {

    private static final Logger log = LoggerFactory.getLogger(ClientWebSocket.class);

    private String uri = "ws://localhost:8050/monopoly/";

    private Session session;

    private static ClientWebSocket instance = null;

    boolean isRunning = false;

    private IClientMessageProcessor messageProcessor;

    public static ClientWebSocket getInstance() {
        if (instance == null) {
            instance = new ClientWebSocket();
        }
        return instance;
    }

    @Override
    public void start() {
        log.info("[WebSocket Client start connection]");
        if (!isRunning) {
            startClient();
            isRunning = true;
        }
    }

    @Override
    public void stop() {
        log.info("[WebSocket Client stop]");
        if (isRunning) {
            stopClient();
            isRunning = false;
        }
    }

    /**
     * Start a WebSocket client.
     */
    private void startClient() {
        log.info("[WebSocket Client start]");
        try {
            WebSocketContainer container = ContainerProvider.getWebSocketContainer();
            container.connectToServer(this, new URI(uri));

        } catch (IOException | URISyntaxException | DeploymentException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Stop the client when it is running.
     */
    private void stopClient(){
        log.info("[WebSocket Client stop]");
        try {
            session.close();
        } catch (IOException ex){
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

    @Override
    public void setMessageProcessor(IClientMessageProcessor handler) {
        this.messageProcessor = handler;
    }

    @Override
    public void onWebSocketMessageReceived(String message, String sessionId)
    {
        Serializer ser = Serializer.getSerializer();
        SocketMessage msg = ser.deserialize(message, SocketMessage.class);
        messageProcessor.processMessage(sessionId, msg.getMessageType(), msg.getMessageData());
    }

    @OnError
    public void onWebSocketError(Session session, Throwable cause) {
        log.info("[WebSocket Client connection error] " + cause.toString());
    }

    @OnClose
    public void onWebSocketClose(CloseReason reason){
        log.info("[WebSocket Client close session] " + session.getRequestURI());
        log.info(" for reason " + reason);
        session = null;
    }

    private void sendMessageToServer(String message)
    {
        try {
            session.getBasicRemote().sendText(message);
        } catch (IOException ex) {
            log.info("[WebSocket Client couldn't send to server] " + session.getRequestURI());
        }
    }

    public void send(Object object)
    {
        SocketMessageGenerator generator = new SocketMessageGenerator();
        String msg = generator.generateMessageString(object);
        sendMessageToServer(msg);
    }
}
