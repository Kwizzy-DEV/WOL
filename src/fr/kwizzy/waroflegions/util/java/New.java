package fr.kwizzy.waroflegions.util.java;

import java.util.*;

public class New {
    @SuppressWarnings("unchecked")
    public static <T> T usingUnsafe(Class<T> clazz){
        try {
            return (T) UnsafeGetter.getUnsafe().allocateInstance(clazz);
        } catch (InstantiationException e) {
            return null;
        }
    }

    public static <T> T usingUnsafe(ParamClass<T> clazz){
        return usingUnsafe(clazz.getClazz());
    }

    public static <T> T usingReflection(Class<T> clazz , Object...args){
        return Reflection.newInstance(clazz , args);
    }

    public static <T> T usingReflection(ParamClass<T> clazz , Object...args){
        return Reflection.newInstance(clazz , args);
    }

    public static<T , U extends Collection<T>> U collection(ParamClass<U> clazz , T...t){
        U coll = clazz.newInstance();
        for(T o : t)coll.add(o);
        return coll;
    }

    public static<T> List<T> list(T...t){
        return collection(new ParamClass<ArrayList<T>>(){}, t);
    }

    public static<T> Set<T> set(T...t){
        return collection(new ParamClass<HashSet<T>>(){}, t);
    }


    public static<T> T[] array(T...t){
        return t;
    }

    @SuppressWarnings("unchecked")
    public static<T> T[] array(Collection<T> t){
        return t.toArray((T[])new Object[t.size()]);
    }

}
