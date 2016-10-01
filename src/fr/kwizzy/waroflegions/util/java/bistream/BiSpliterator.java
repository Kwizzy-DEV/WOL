package fr.kwizzy.waroflegions.util.java.bistream;

import java.util.Spliterator;
import java.util.function.Consumer;

public class BiSpliterator<T , U> implements Spliterator<BiStream.BiValue<T , U>>{

    private Spliterator<T> t;
    private Spliterator<U> u;

    BiSpliterator(Spliterator<T> t, Spliterator<U> u) {
        this.t = t;
        this.u = u;
    }

    @Override
    public boolean tryAdvance(Consumer<? super BiStream.BiValue<T, U>> action) {
        if (action == null) throw new NullPointerException();
        BiStream.BiValue<T , U> b = new BiStream.BiValue<>(null , null);
        boolean tr = this.t.tryAdvance((t) -> b.t = t);
        boolean ur = this.u.tryAdvance((u) -> b.u = u);
        if(!tr && !ur)return false;
        action.accept(b);
        return true;
    }

    @Override
    public Spliterator<BiStream.BiValue<T, U>> trySplit() {
        return new BiSpliterator<>(t.trySplit() , u.trySplit());
    }

    @Override
    public long estimateSize() {
        long t = this.t.estimateSize();
        long u = this.u.estimateSize();
        return t < u ? t : u;
    }

    @Override
    public int characteristics() {
        return t.characteristics() | u.characteristics();
    }
}
