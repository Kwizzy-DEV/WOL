package fr.kwizzy.waroflegions.util.java;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public class Types {
    @SuppressWarnings("unchecked")
    public static<T> Class<T> getClazz(Type type){
        if(type instanceof Class<?>)return (Class<T>)type;
        else if(type instanceof ParameterizedType)return (Class<T>)((ParameterizedType)type).getRawType();
        else return null;
    }
}
