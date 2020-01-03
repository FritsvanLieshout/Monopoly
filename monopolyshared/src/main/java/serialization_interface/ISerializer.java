package serialization_interface;

import java.lang.reflect.Type;

public interface ISerializer<R> {
    <T> T deserialize(String data, Class<T> clazz);
    <T> T deserialize(String data, Type type);
    R serialize(Object object);

}
