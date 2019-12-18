package messaging;

import server_interface.IServerHandlerFactory;
import server_interface.IServerMessageHandler;

public class ServerHandlerFactory implements IServerHandlerFactory {
    public IServerMessageHandler getHandler(String classname) {
        if ("UserTestMessage".equals(classname)) {
           // return new TestHandler();
            return null;
        }
        return null;
    }
}
