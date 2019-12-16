package server;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.websocket.jsr356.server.deploy.WebSocketServerContainerInitializer;

import javax.websocket.server.ServerContainer;

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
        //IServerHandlerFactory factory = new ServerHandler();
        //IServerMessageProcessor messageProcessor = new ServerMessageProcessor(factory);
        //final IServerWebSocket socket = new ServerWebSocket();
        //socket.setMessageProcessor(messageProcessor);

        //IServerMessageGenerator messageGenerator = new ServerMessageGenerator(socket);
        //IGame game = new Game(messageGenerator);
        //messageProcessor.register(game);


        Server webSocketServer = new Server();
        ServerConnector connector = new ServerConnector(webSocketServer);
        connector.setPort(PORT);
        webSocketServer.addConnector(connector);

        // Setup the basic application "context" for this application at "/"
        // This is also known as the handler tree (in jetty speak)
        ServletContextHandler webSocketContext = new ServletContextHandler(ServletContextHandler.SESSIONS);
        webSocketContext.setContextPath("/");
        webSocketServer.setHandler(webSocketContext);

        try {
            // Initialize javax.websocket layer
            ServerContainer wscontainer = WebSocketServerContainerInitializer.configureContext(webSocketContext);

            // Add WebSocket endpoint to javax.websocket layer
            wscontainer.addEndpoint(ServerWebSocket.class);

            webSocketServer.start();
            //server.dump(System.err);

            webSocketServer.join();
        } catch (Throwable t) {
            t.printStackTrace(System.err);
        }
    }

}
