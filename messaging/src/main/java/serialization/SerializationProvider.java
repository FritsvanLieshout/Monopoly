package serialization;

import serialization_interface.ISerializer;

public class SerializationProvider {

    private static ISerializer serializer;

    public static ISerializer getSerializer()
    {
        if(serializer == null) serializer = new Serializer();
        return serializer;
    }

}
