package chirc.IRCCommandHandler;

import actor.Actor;
import chirc.IRCConnection;
import chirc.IRCMessage;
import chirc.IRCServer;
import fpinjava.Result;

public class Context {

    private Strategy strategy;

    public Context(Strategy strategy) {
        this.strategy = strategy;
    }

    public void executeStrategy(IRCConnection connection, IRCMessage message, IRCServer server, Result<Actor<String>> sender, Result<Actor<String>> ircactor) {
        strategy.executeCommand(connection, message, server, sender, ircactor);
    }
}
