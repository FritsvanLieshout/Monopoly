package messaging;

public class ServerHandlerFactory {
    public IServerMessageHandler getHandler(String classname) {
        switch (classname) {
            case "UserTestMessage":
                return new TestHandler();
            default:
                return null;
        }
    }
}
