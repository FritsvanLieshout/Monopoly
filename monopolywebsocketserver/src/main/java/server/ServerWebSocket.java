package server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import server_interface.IServerMessageProcessor;
import server_interface.IServerWebSocket;
import messages.SocketMessage;
import messages.SocketMessageGenerator;
import serialization.Serializer;

import javax.inject.Singleton;
import javax.websocket.server.ServerEndpoint;
import javax.websocket.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Singleton
@ServerEndpoint(value="/monopoly/")
public class ServerWebSocket implements IServerWebSocket {

    private ExecutorService executorService = Executors.newScheduledThreadPool(10);
    private static final Logger log = LoggerFactory.getLogger(ServerWebSocket.class);

    private static ServerWebSocket instance = null;

    public static ServerWebSocket getInstance(){
        if (instance == null) {
            instance = new ServerWebSocket();
        }
        return instance;
    }

    private IServerMessageProcessor messageProcessor;

    public IServerMessageProcessor getMessageProcessor() {
        return messageProcessor;
    }

    public void setMessageProcessor(IServerMessageProcessor processor)
    {
        this.messageProcessor = processor;
    }

    private static ArrayList<Session> sessions = new ArrayList<>();

    @OnOpen
    public void onConnect(Session session) {
        sessions.add(session);
    }

    @OnMessage
    public void onText(String message, Session session) {
        String sessionId = session.getId();
        Serializer ser = Serializer.getSerializer();
        SocketMessage msg = ser.deserialize(message, SocketMessage.class);
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                getMessageProcessor().processMessage(sessionId, msg.getMessageType(), msg.getMessageData());
            }
        });
    }

    @OnClose
    public void onClose(CloseReason reason, Session session) {
        getMessageProcessor().handleDisconnect(session.getId());
        sessions.remove(session);
    }

    @OnError
    public void onError(Throwable cause, Session session) {
        log.info(cause.getMessage());
    }

    public void sendTo(String sessionId, Object object)
    {
        SocketMessageGenerator generator = new SocketMessageGenerator();
        String msg = generator.generateMessageString(object);
        sendToClient(getSessionFromId(sessionId), msg);
    }

    public Session getSessionFromId(String sessionId)
    {
        for(Session s : sessions)
        {
            if(s.getId().equals(sessionId))
                return s;
        }
        return null;
    }

    public void broadcast(Object object)
    {
        for(Session session : sessions) {
            sendTo(session.getId(), object);
        }
    }

    public void sendToOthers(String sessionId, Object object)
    {
        Session session = getSessionFromId(sessionId);
        for(Session ses : sessions) {
            if(!ses.getId().equals(session.getId()))
                sendTo(ses.getId(), object);
        }
    }

    private void sendToClient(Session session, String message)
    {
        try {
            if (session != null) session.getBasicRemote().sendText(message);
            else return;
        } catch (IOException e) {
            log.info(e.getMessage());
        }
    }
}
