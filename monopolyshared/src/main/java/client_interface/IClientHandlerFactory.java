package client_interface;

public interface IClientHandlerFactory {
    IClientMessageHandler getHandler(String classname);
}
