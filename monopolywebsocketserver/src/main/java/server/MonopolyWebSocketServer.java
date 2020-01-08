package server;

import logic.GameLogic;
import logic_interface.IGameLogic;
import messaging.ServerHandlerFactory;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.websocket.jsr356.server.deploy.WebSocketServerContainerInitializer;
import server_interface.IServerHandlerFactory;
import server_interface.IServerMessageGenerator;
import server_interface.IServerMessageProcessor;
import server_interface.IServerWebSocket;

import javax.websocket.server.ServerContainer;
import javax.websocket.server.ServerEndpoint;
import javax.websocket.server.ServerEndpointConfig;

public class MonopolyWebSocketServer {

    private static final int PORT = 8050;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        startWebSocketServer();
    }

    // Start the web socket server
    private static void startWebSocketServer() {
        IServerHandlerFactory factory = new ServerHandlerFactory();
        IServerMessageProcessor messageProcessor = new ServerMessageProcessor(factory);
        final IServerWebSocket socket = new ServerWebSocket();
        socket.setMessageProcessor(messageProcessor);

        IServerMessageGenerator messageGenerator = new ServerMessageGenerator(socket);

        IGameLogic game = new GameLogic(messageGenerator);
        messageProcessor.registerGame(game);

        Server webSocketServer = new Server();
        ServerConnector connector = new ServerConnector(webSocketServer);
        connector.setPort(PORT);
        webSocketServer.addConnector(connector);
        //webSocketServer.setStopAtShutdown(true);
        //webSocketServer.setStopTimeout(2000000);

        // Setup the basic application "context" for this application at "/"
        // This is also known as the handler tree (in jetty speak)
        ServletContextHandler webSocketContext = new ServletContextHandler(ServletContextHandler.SESSIONS);
        webSocketContext.setContextPath("/");
        webSocketServer.setHandler(webSocketContext);

        try {
            // Initialize javax.websocket layer
            ServerContainer wscontainer = WebSocketServerContainerInitializer.configureContext(webSocketContext);
//
//            // Add WebSocket endpoint to javax.websocket layer
//            wscontainer.addEndpoint(ServerWebSocket.class);
//
//            webSocketServer.start();
//            //server.dump(System.err);
//
//            webSocketServer.join();
            ServerEndpointConfig config = ServerEndpointConfig.Builder.create(socket.getClass(), socket.getClass().getAnnotation(ServerEndpoint.class).value()).configurator(new ServerEndpointConfig.Configurator() {
                @Override
                public <T> T getEndpointInstance(Class<T> endpointClass) {
                    return (T) socket;
                }
            }).build();
            wscontainer.addEndpoint(config);
            webSocketServer.start();
            webSocketServer.join();
        } catch (Throwable t) {
            t.printStackTrace(System.err);
        }
    }

}
