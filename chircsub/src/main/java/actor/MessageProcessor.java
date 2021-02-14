package actor;

import fpinjava.Result;

public interface MessageProcessor<T> {

    /**
     * process method where we process a message
     *
     * @param t      the type
     * @param sender the sender
     */
    void process(T t, Result<Actor<T>> sender);
}
