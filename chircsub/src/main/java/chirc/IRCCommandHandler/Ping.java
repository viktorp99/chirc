package chirc.IRCCommandHandler;

import actor.Actor;
import chirc.IRCConnection;
import chirc.IRCMessage;
import chirc.IRCServer;
import fpinjava.Result;

public class Ping implements Strategy {
    @Override
    public void executeCommand(IRCConnection connection, IRCMessage message, IRCServer server, Result<Actor<String>> sender, Result<Actor<String>> ircactor) {
        if (message.getParameters().size() == 1) {
            sender.forEach(actor -> actor.tell(":" + connection.getServerHost() + "PONG" + message.getParameter(0)));
        } else if (message.getParameters().size() == 2) {
            sender.forEach(actor -> actor.tell(":" + connection.getServerHost() + "PONG" + message.getParameter(0) + " " + message.getParameter(1)));
        }
    }
}
