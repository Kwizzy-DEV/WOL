package fr.kwizzy.waroflegions.util.java;

import java.util.Arrays;

public class ArraysUtils {

    @SuppressWarnings("unchecked")
    public static <T> T[] combine(T[]...arrays){
        T[] finalArray = (T[]) new Object[Arrays.stream(arrays).mapToInt(array -> array.length).sum()];
        int i = 0;
        for(T[] array : arrays){
            System.arraycopy(array , 0 , finalArray , i , array.length);
            i += array.length;
        }
        return finalArray;
    }

    public static int[] toInts(char[] chars){
        int[] ints = new int[chars.length];

        for (int i = 0; i < ints.length; i++) ints[i] = chars[i];

        return ints;
    }

    public static byte[] toBytes(char[] chars){
        byte[] bytes = new byte[chars.length];

        for (int i = 0; i < bytes.length; i++) bytes[i] = (byte) chars[i];

        return bytes;
    }
}
