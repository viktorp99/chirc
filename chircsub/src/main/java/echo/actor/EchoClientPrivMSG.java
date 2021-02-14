package echo.actor;

import actor.ActorSystem;
import actor.AskWithOutput;
import actor.Writer;
import inout.ConsoleWriter;

public class EchoClientPrivMSG {

    public static void main(String[] args) throws InterruptedException {
        String host = "localhost";
        int port = 8080;

        Writer wr = ActorSystem.actorSelection(host, port, false);
        ConsoleWriter cwr = ConsoleWriter.consoleWriter();

        AskWithOutput.ask(wr, "NICK viktor", cwr);
        AskWithOutput.ask(wr, "USER viktor :viktor Popov", cwr); // successful registration
        Thread.sleep(5000);

        AskWithOutput.ask(wr,"PRIVMSG pp99 :Whats up!",cwr);
        Thread.sleep(5000);

        AskWithOutput.ask(wr,"QUIT :Gone to have lunch",cwr);
        Thread.sleep(20000);

    }
}
