package serialization;

import com.google.gson.Gson;

import java.lang.reflect.Type;

public class Serializer {
    private static Serializer serializer;

    public static Serializer getSerializer()
    {
        if(serializer == null) serializer = new Serializer();
        return serializer;
    }

    private final Gson gson = new Gson();

    public <T> T deserialize(String data, Class<T> tClass) {
        return gson.fromJson(data, tClass);
    }

    public <T> T deserialize(String data, Type type) {
        return gson.fromJson(data, type);
    }

    public String serialize(Object object) {
        return gson.toJson(object);
    }
}
