package serialization;

import serialization_interface.ISerializer;

import java.lang.reflect.Type;

public abstract class BaseSerializer<R> implements ISerializer<R> {

    public abstract <T> T deserialize(String data, Class<T> clazz);

    public abstract <T> T deserialize(String data, Type type);

    public abstract R serialize(Object object);

}
