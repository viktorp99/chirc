package actor;

import fpinjava.Result;
import inout.InputOutput;
import inout.TCPSocket;

public interface ActorSocket {


    /**
     * Helper method that provides Resutl.Empty with the Result<Actor> type
     *
     * @param <InputOutput> the type of the Actor
     * @return - empty result
     */
    static <InputOutput> Result<Actor<InputOutput>> noSender() {
        return Result.empty();
    }

    /**
     * the self method returns a reference to itself
     *
     * @return this actor
     */
    Result<Actor<InputOutput>> self();

    /**
     * Method that accesses the context of an actor
     *
     * @return - this actors context
     */
    ActorContext<InputOutput> getContext();

    /**
     * tell method to send messages without indicating the sender
     *
     * @param message - the messages to be send
     */
    default void tell(String message) {
        tell(message, self());
    }

    /**
     * tell method that sends messages with indicating the sender as Result<Actor>
     *
     * @param message - the messages to be send
     * @param sender  - the Result<Actor> sender
     */
    void tell(String message, Result<Actor<InputOutput>> sender);

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
    default void tell(String message, Actor<InputOutput> sender) {
        tell(message, Result.of(sender));
    }

    /**
     * Enum type to keep the configuration of the actors
     */
    enum Type {
        SERIAL, PARALLEL
    }

}
