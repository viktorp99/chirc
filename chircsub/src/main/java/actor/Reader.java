package actor;

import chirc.IRCConnection;
import fpinjava.Result;
import inout.Input;
import list.List;
import stream.Stream;

public class Reader extends AbstractActor<String> {

    private Input input;
    private final boolean transceiverMode;
    private final Writer writer;

    public Reader(String id, Type type, Input input) {
        super(id, type);
        this.input = input;
        this.transceiverMode = false;
        this.writer = null;
    }

    public Reader(String id, Type type, Input input, Writer writer, boolean transceiverMode) {
        super(id, type);
        this.input = input;
        this.transceiverMode = transceiverMode;
        this.writer = writer;
    }

    @Override
    public synchronized void onReceive(String message, Result<Actor<String>> sender) {
        // System.out.println(message + "-- Reader " + sender.successValue().toString());
        input.lines().forEach(n -> sender.forEach(s -> s.tell(n, writer)));

        if (!transceiverMode)
            sender.forEach(n -> n.tell("\u0004", self()));
    }


    @Override
    public IRCConnection getConnection() {
        return null;
    }
}