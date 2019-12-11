package messages;

public class SocketMessage {
    private String messageType;
    private String messageData;

    public SocketMessage(String messageType, String messageData) {
        this.messageType = messageType;
        this.messageData = messageData;
    }

    public String getMessageType() { return this.messageType; }

    public String getMessageData() { return this.messageData; }
}
