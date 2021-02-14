package echo.actor;

import actor.ActorSystem;
import actor.AskWithOutput;
import actor.Writer;
import inout.ConsoleWriter;

public class EchoClientRegister {
    public static void main(String[] args) throws InterruptedException {

        String host = "localhost";
        int port = 8080;

        Writer wr = ActorSystem.actorSelection(host, port, false);
        ConsoleWriter cwr = ConsoleWriter.consoleWriter();
        AskWithOutput.ask(wr, "NICK pp99", cwr);
        Thread.sleep(1000);
        AskWithOutput.ask(wr, "USER papi :Papi Hans", cwr);
        Thread.sleep(70000);

    }
}
