package fpinjava;

public interface Effect<T> {
    void apply(T t);
}