package echo.actor;

import actor.ActorSystem;
import actor.AskWithOutput;
import actor.Writer;
import inout.ConsoleWriter;

import javax.swing.*;

public class EchoClientQuit {

    public static void main(String[] args) throws InterruptedException {
        String host = "localhost";
        int port = 8080;

        Writer wr = ActorSystem.actorSelection(host, port, false);
        ConsoleWriter cwr = ConsoleWriter.consoleWriter();
        AskWithOutput.ask(wr,"QUIT :Gone to have lunch",cwr); // must be registered
        AskWithOutput.ask(wr,"QUhsbdashdbIT :Gone to have lunch",cwr); // ignored
        Thread.sleep(2000);
        AskWithOutput.ask(wr, "NICK viktor", cwr);
        AskWithOutput.ask(wr, "USER viktor :viktor Popov", cwr); // successful registration
        Thread.sleep(5000);
        AskWithOutput.ask(wr,"QUIT :Gone to have lunch",cwr);// exiting
        Thread.sleep(5000);
        Thread.sleep(20000);

    }
}
