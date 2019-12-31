package server_interface;

public interface IServerHandlerFactory {
    IServerMessageHandler getHandler(String classname, Object game);
}
