package chirc.IRCCommandHandler;

import actor.Actor;
import chirc.IRCConnection;
import chirc.IRCMessage;
import chirc.IRCServer;
import fpinjava.Result;

public class Quit implements Strategy {
    @Override
    public void executeCommand(IRCConnection connection, IRCMessage message, IRCServer server, Result<Actor<String>> sender, Result<Actor<String>> ircactor) {
        server.closeConnection(connection);
        if (message.getParameters().size() < 1) {
            sender.forEach(actor -> actor.tell("Closing link: " + connection.getClient().getNick() + "!" + connection.getClient().getUsername() + "@" + connection.getSocket().getInetAddress().getCanonicalHostName() + "(" + "Client Quit" + ")\r\n", ircactor.successValue()));
        } else {
            sender.forEach(actor -> actor.tell("Closing link: " + connection.getClient().getNick() + "!" + connection.getClient().getUsername() + "@" + connection.getSocket().getInetAddress().getCanonicalHostName() + " (" + message.getParameter(0) + ")\r\n", ircactor.successValue()));
        }
    }
}
