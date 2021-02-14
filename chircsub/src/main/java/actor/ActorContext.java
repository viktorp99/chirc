package actor;

public interface ActorContext<T> {

    /**
     * become method that changes behavior by receiving new MessageProcessor
     *
     * @param behavior - the new MessageProcessor
     */
    void become(MessageProcessor<T> behavior);

    /**
     * getter for the actors behavior
     *
     * @return - the MessageProcessor of the actor
     */
    MessageProcessor<T> getBehavior();

}
