package fr.kwizzy.waroflegions.util.java;

import sun.misc.Unsafe;

public class UnsafeGetter {
    private static Unsafe unsafe = Reflection.get(Unsafe.class , "theUnsafe" , Unsafe.class);

    public static Unsafe getUnsafe() {
        return unsafe;
    }

    public static long sizeOf(Object object){
        return getUnsafe().getAddress(
                normalize(getUnsafe().getInt(object, 4L)) + 12L);
    }

    private static long normalize(int value) {
        if(value >= 0) return value;
        return (~0L >>> 32) & value;
    }
}
