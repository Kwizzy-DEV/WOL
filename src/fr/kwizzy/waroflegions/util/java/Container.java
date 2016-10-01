package fr.kwizzy.waroflegions.util.java;

public class Container<T> {
    private T value;

    public Container(T value) {
        this.value = value;
    }

    public Container() {}

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }
}
