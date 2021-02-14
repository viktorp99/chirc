package actor;

import chirc.IRCConnection;
import fpinjava.Result;
import inout.Input;
import inout.Output;

public class Writer extends AbstractActor<String> /* implements MessageProcessor<String> */ {

    private Input input;
    private Output output;
    private Reader reader;
    final private boolean transceiverMode;
    public boolean terminated;
    private IRCConnection connection;

    public Writer(Input input, Output output, boolean transceiverMode) {
        super("Writer", Actor.Type.SERIAL);
        this.input = input;
        this.output = output;
        this.reader = new Reader("Reader", Actor.Type.SERIAL, input, this, transceiverMode);
        this.transceiverMode = transceiverMode;
        terminated = false;
        this.connection = null;
    }

    public Writer(Input input, Output output) {
        super("Writer", Actor.Type.SERIAL);
        this.input = input;
        this.output = output;
        this.reader = new Reader("Reader", Actor.Type.SERIAL, input);
        this.transceiverMode = false;
        terminated = false;
        this.connection = null;
    }

    public void start() {
        reader.tell("", self());
    }

    public void start(Result<Actor<String>> consumer) {
        //System.out.println(transceiverMode);
        reader.tell("", consumer.successValue());
    }

    @Override
    public synchronized void onReceive(String message, Result<Actor<String>> sender) {
        /*reader.tell("", self());

        this.getContext().become(this.getContext().getBehavior());
        */

        if (message.equals("\u0004")) {
            if (transceiverMode)
                output.printLine(message);
            output.shutdownOutput();
            terminated = true;
        } else {
            //System.err.println(message + output.toString());
            output.printLine(message);
        }
    }

    @Override
    public IRCConnection getConnection() {
        return this.connection;
    }

    public void setConnection(IRCConnection connection){
        this.connection = connection;
    }

/*
    final public void process(String message, Result<Actor<String>> sender) {
        if
        (message.equals("\u0004")) {
            System.err.println("ENDDD"); //
            output.shutdownOutput();
        } else { // System.err.println(message);
            output.printLine(message);
        }
    }
*/

}
