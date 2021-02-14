package actor;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

public class UserThreadFactory implements ThreadFactory {

    @Override
    public Thread newThread(Runnable r) {
        Thread thread = Executors.defaultThreadFactory().newThread(r);
        thread.setDaemon(false);
        return thread;
    }

}
