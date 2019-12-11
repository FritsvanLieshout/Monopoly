package messages;

import serialization.Serializer;

public class SocketMessageGenerator {

    Serializer ser = Serializer.getSerializer();

    public String generateMessageString(Object content)
    {
        SocketMessage msg = generateMessage(content);
        return ser.serialize(msg);
    }

    public SocketMessage generateMessage(Object content)
    {
        String messageForServerJson = ser.serialize(content);
        String type = content.getClass().toGenericString();
        return new SocketMessage(type, messageForServerJson);
    }
}
