package fr.kwizzy.waroflegions.util.nms.tracker;

import java.util.Collection;
import java.util.HashSet;

public class EventHashSet<E> extends HashSet<E>{
    private final HashSetEventHandler<E> eventHandler;

    public interface HashSetEventHandler<E>{
        boolean onAdd(E e);
        boolean onRemove(Object o);
    }

    public EventHashSet(HashSetEventHandler<E> eventHandler) {
        super();
        this.eventHandler = eventHandler;
    }

    public EventHashSet(Collection<? extends E> collection , HashSetEventHandler<E> eventHandler) {
        super(collection);
        this.eventHandler = eventHandler;
    }

    public EventHashSet(int i, float v , HashSetEventHandler<E> eventHandler) {
        super(i, v);
        this.eventHandler = eventHandler;
    }

    public EventHashSet(int i , HashSetEventHandler<E> eventHandler) {
        super(i);
        this.eventHandler = eventHandler;
    }

    @Override
    public boolean add(E e) {
        return eventHandler.onAdd(e) && super.add(e);
    }

    @Override
    public boolean remove(Object o) {
        return eventHandler.onRemove(o) && super.remove(o);
    }
}
