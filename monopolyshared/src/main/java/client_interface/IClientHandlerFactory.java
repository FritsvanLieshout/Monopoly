package client_interface;

public interface IClientHandlerFactory {
    IClientMessageHandler getHandler(String simpletType, Object game);
}
