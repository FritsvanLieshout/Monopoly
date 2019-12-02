package communicatorshared;

public class CommunicatorWebSocketMessage {
    // Operation that is requested at client side
    private CommunicatorWebSocketMessageOperation operation;

    // Property
    private String property;
    private Object object;

    // Content
    private String content;

    public CommunicatorWebSocketMessageOperation getOperation() {
        return operation;
    }

    public void setOperation(CommunicatorWebSocketMessageOperation operation) {
        this.operation = operation;
    }

    public String getProperty() {
        return property;
    }

    public void setProperty(String property) {
        this.property = property;
    }

    public String getContent() {
        return content;
    }

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
