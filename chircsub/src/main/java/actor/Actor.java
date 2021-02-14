package actor;

import chirc.IRCConnection;
import fpinjava.Result;

public interface Actor<T> {

    /**
     * Helper method that provides Resutl.Empty with the Result<Actor> type
     *
     * @param <T> the type of the Actor
     * @return - empty result
     */
    static <T> Result<Actor<T>> noSender() {
        return Result.empty();
    }

    /**
     * the self method returns a reference to itself
     *
     * @return this actor
     */
    Result<Actor<T>> self();

    /**
     * Method that accesses the context of an actor
     *
     * @return - this actors context
     */
    ActorContext<T> getContext();

    /**
     * tell method to send messages without indicating the sender
     *
     * @param message - the messages to be send
     */
    default void tell(T message) {
        tell(message, self());
    }

    /**
     * tell method that sends messages with indicating the sender as Result<Actor>
     *
     * @param message - the messages to be send
     * @param sender  - the Result<Actor> sender
     */
    void tell(T message, Result<Actor<T>> sender);

    /**
     * shutdown method that terminates the actor
     */
    void shutdown();

    /**
     * tell method that sends messages without directly indicating the result sender
     * but the reference instead
     *
     * @param message - the messages to be send
     * @param sender  - the Actor<T> sender
     */
    default void tell(T message, Actor<T> sender) {
        tell(message, Result.of(sender));
    }

    /**
     * Enum type to keep the configuration of the actors
     */
    enum Type {
        SERIAL, PARALLEL
    }

    IRCConnection getConnection();

}
