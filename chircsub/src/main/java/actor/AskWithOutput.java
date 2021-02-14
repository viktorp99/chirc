package actor;

import chirc.IRCConnection;
import fpinjava.Result;
import inout.Output;

public class AskWithOutput extends AbstractActor<String> {

    private Output output;

    private AskWithOutput(Output output) {
        super("AskWithOutput", Actor.Type.SERIAL);
        this.output = output;
    }

    public static void ask(Actor<String> actor, String message, Output output) {
        AskWithOutput awo = new AskWithOutput(output);
        // System.out.println("nowwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwww");
        // actor.tell(message, awo);

        actor.tell(message, awo);

        // System.err.println("DONE");
    }

    public static void ask(Writer actor, String message, Output output) {
        AskWithOutput awo = new AskWithOutput(output);
        //System.out.println(1);
        actor.tell(message, awo);
        actor.start(Result.success(awo));
        //System.out.println("here");

    }

    @Override
    public void onReceive(String message, Result<Actor<String>> sender) {
        output.printLine(message);

    }

    @Override
    public IRCConnection getConnection() {
        return null;
    }
}
