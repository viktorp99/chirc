package actor;

import chirc.IRCConnection;
import fpinjava.Result;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.RejectedExecutionException;

public abstract class AbstractActor<T> implements Actor<T> {

    private final ActorContext<T> context;
    private final String id;
    private final ExecutorService executor;

    /**
     * constructor of the class that initializes an object of the class with the
     * given id and type arguments
     *
     * @param id   - the id of the object
     * @param type - the actors type
     */
    public AbstractActor(String id, Actor.Type type) {
        super();
        this.id = id;
        this.executor = type == Type.SERIAL ? Executors.newSingleThreadExecutor(new DaemonThreadFactory())
                : Executors.newCachedThreadPool(new DaemonThreadFactory());
        this.context = new ActorContext<T>() {
            private MessageProcessor<T> behavior = AbstractActor.this::onReceive;

            @Override
            public synchronized void become(MessageProcessor<T> behavior) {
                this.behavior = behavior;
            }

            @Override
            public MessageProcessor<T> getBehavior() {
                return behavior;
            }
        };
    }

    /**
     * abstract method that holds the business processing -
     *
     * @param message the message
     * @param sender  - the sender
     */
    public abstract void onReceive(T message, Result<Actor<T>> sender);

    /**
     * identity method that returns the same actor object as result of actor
     */
    public Result<Actor<T>> self() {
        return Result.success(this);
    }

    /**
     * getter for the context of the actor
     */
    public ActorContext<T> getContext() {
        return this.context;
    }

    /**
     * shutdown method that terminates the actor
     */
    @Override
    public void shutdown() {
        this.executor.shutdown();
    }

    /**
     * tell method that describes how an actor receives a message. Synchronized so
     * that messages are processed one at a time
     */
    public synchronized void tell(final T message, Result<Actor<T>> sender) {
        executor.execute(() -> {
            try {
                context.getBehavior().process(message, sender);
            } catch (RejectedExecutionException e) {

            } catch (Exception e) {
                throw new RuntimeException(e);
            }

        });
    }


}
