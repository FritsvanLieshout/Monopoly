package messages;

public class PropertyAlreadyOwnedMessage {

    private int ownerId;

    public PropertyAlreadyOwnedMessage(int ownerId) { this.ownerId = ownerId; }

    public int getOwnerId() { return ownerId; }
}
