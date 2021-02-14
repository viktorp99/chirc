package chirc.IRCCommandHandler;

import actor.Actor;
import chirc.IRCConnection;
import chirc.IRCMessage;
import chirc.IRCServer;
import fpinjava.Result;

public interface Strategy {

    void executeCommand(IRCConnection connection, IRCMessage message, IRCServer server, Result<Actor<String>> sender, Result<Actor<String>> ircactor);
}
