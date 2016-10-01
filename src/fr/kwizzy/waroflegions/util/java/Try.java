package fr.kwizzy.waroflegions.util.java;

import java.util.*;

public class Try {
    public static boolean debug = false;

    @FunctionalInterface
    public interface TryRunnable<T>{
        T run() throws Throwable;
    }

    @FunctionalInterface
    public interface TryVoidRunnable{
        void run() throws Throwable;
    }


    public static<T> T or(TryRunnable<T> runnable , T defaultValue , boolean stackTrace){
        try {
            return runnable.run();
        } catch (Throwable t) {
            if(stackTrace)t.printStackTrace();
            return defaultValue;
        }
    }

    public static<T> T or(TryRunnable<T> runnable , T defaultValue){
        return or(runnable , defaultValue , debug);
    }

    public static void orVoid(TryVoidRunnable runnable ,  boolean stackTrace){
        try {
            runnable.run();
        } catch (Throwable t) {
            if(stackTrace)t.printStackTrace();
        }
    }

    public static void orVoid(TryVoidRunnable runnable){
        orVoid(runnable , debug);
    }

    public static<T> T orNull(TryRunnable<T> runnable , boolean stackTrace){
        return or(runnable , null , stackTrace);
    }

    public static<T> T orNull(TryRunnable<T> runnable){
        return or(runnable , null);
    }

    public static boolean orFalse(TryRunnable<Boolean> runnable , boolean stackTrace){
        return or(runnable , false , stackTrace);
    }

    public static boolean orFalse(TryRunnable<Boolean> runnable){
        return or(runnable , false);
    }

    public static boolean orTrue(TryRunnable<Boolean> runnable , boolean stackTrace){
        return or(runnable , true , stackTrace);
    }

    public static boolean orTrue(TryRunnable<Boolean> runnable){
        return or(runnable , true);
    }

    public static Number or0(TryRunnable<Number> runnable , boolean stackTrace){
        return or(runnable , 0 , stackTrace);
    }

    public static Number or0(TryRunnable<Number> runnable){
        return or(runnable , 0);
    }

    public static <T> Collection<T> orEmpty(TryRunnable<Collection<T>> runnable , boolean stackTrace){
        return or(runnable , Collections.emptyList(), stackTrace);
    }

    public static <T> List<T> orEmptyList(TryRunnable<List<T>> runnable , boolean stackTrace){
        return or(runnable , Collections.emptyList(), stackTrace);
    }

    public static <T> List<T> orEmptyList(TryRunnable<List<T>> runnable){
        return or(runnable , Collections.emptyList());
    }

    public static <T> Set<T> orEmptySet(TryRunnable<Set<T>> runnable , boolean stackTrace){
        return or(runnable , Collections.emptySet(), stackTrace);
    }

    public static <T> Set<T> orEmptySet(TryRunnable<Set<T>> runnable){
        return or(runnable , Collections.emptySet());
    }

    public static <T> NavigableSet<T> orEmptyNavigableSet(TryRunnable<NavigableSet<T>> runnable , boolean stackTrace){
        return or(runnable , Collections.emptyNavigableSet(), stackTrace);
    }

    public static <T> NavigableSet<T> orEmptyNavigableSet(TryRunnable<NavigableSet<T>> runnable){
        return or(runnable , Collections.emptyNavigableSet());
    }

    public static <T> SortedSet<T> orEmptySortedSet(TryRunnable<SortedSet<T>> runnable , boolean stackTrace){
        return or(runnable , Collections.emptySortedSet(), stackTrace);
    }

    public static <T> SortedSet<T> orEmptySortedSet(TryRunnable<SortedSet<T>> runnable){
        return or(runnable , Collections.emptySortedSet());
    }

    public static <T> Enumeration<T> orEmptyEnumeration(TryRunnable<Enumeration<T>> runnable , boolean stackTrace){
        return or(runnable , Collections.emptyEnumeration(), stackTrace);
    }

    public static <T> Enumeration<T> orEmptyEnumeration(TryRunnable<Enumeration<T>> runnable){
        return or(runnable , Collections.emptyEnumeration());
    }

    public static <T> Iterator<T> orEmptyIterator(TryRunnable<Iterator<T>> runnable , boolean stackTrace){
        return or(runnable , Collections.emptyIterator(), stackTrace);
    }

    public static <T> Iterator<T> orEmptyIterator(TryRunnable<Iterator<T>> runnable){
        return or(runnable , Collections.emptyIterator());
    }

    public static <T> ListIterator<T> orEmptyListIterator(TryRunnable<ListIterator<T>> runnable , boolean stackTrace){
        return or(runnable , Collections.emptyListIterator(), stackTrace);
    }

    public static <T> ListIterator<T> orEmptyListIterator(TryRunnable<ListIterator<T>> runnable){
        return or(runnable , Collections.emptyListIterator());
    }

    public static <K , V> Map<K , V> orEmptyMap(TryRunnable<Map<K , V>> runnable , boolean stackTrace){
        return or(runnable , Collections.emptyMap(), stackTrace);
    }

    public static <K , V> Map<K , V> orEmptyMap(TryRunnable<Map<K , V>> runnable){
        return or(runnable , Collections.emptyMap());
    }

    public static <K , V> NavigableMap<K , V> orEmptyNavigableMap(TryRunnable<NavigableMap<K , V>> runnable , boolean stackTrace){
        return or(runnable , Collections.emptyNavigableMap(), stackTrace);
    }

    public static <K , V> NavigableMap<K , V> orEmptyNavigableMap(TryRunnable<NavigableMap<K , V>> runnable){
        return or(runnable , Collections.emptyNavigableMap());
    }

    public static <K , V> SortedMap<K , V> orEmptySortedMap(TryRunnable<SortedMap<K , V>> runnable , boolean stackTrace){
        return or(runnable , Collections.emptySortedMap(), stackTrace);
    }

    public static <K , V> SortedMap<K , V> orEmptySortedMap(TryRunnable<SortedMap<K , V>> runnable){
        return or(runnable , Collections.emptySortedMap());
    }
}
