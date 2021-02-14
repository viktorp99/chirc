package chirc.IRCCommandHandler;

import actor.Actor;
import chirc.Client;
import chirc.IRCConnection;
import chirc.IRCMessage;
import chirc.IRCServer;
import fpinjava.Result;

public class RegisterUser implements Strategy {

    @Override
    public void executeCommand(IRCConnection connection, IRCMessage message, IRCServer server, Result<Actor<String>> sender, Result<Actor<String>> ircactor) {
        if (!IRCMessage.validateParams(message, 2)) {
            sender.forEach(actor -> actor.tell(":" + connection.getServerHost() + " " + Reply.ERR_NEEDMOREPARAMS + "USER <username> :<name> <surname> ..." + " :The command USER requires more arguments ...\r\n", ircactor.successValue()));
            return;
            // not enough arguments
        }
        if (connection.isRegistered()) {
            sender.forEach(actor -> actor.tell(":" + connection.getServerHost() + " " + Reply.ERR_ALREADYREGISTERED + " " + connection.getClient().getNick() + " :The user is already registered, try to sign in\r\n" + connection.getClient().getNick() + "!" + connection.getClient().getUsername() + "@" + connection.getSocket().getInetAddress().getHostName() + "\r\n", ircactor.successValue()));
            // user already registered
        } else {
            if (connection.isRegisteringStarted()) {
                String nick = connection.getClient().getNick();
                String username = message.getParameter(0);
                String realname = message.getParameter(1);
                Client client = new Client(nick, username, realname);
                server.registerConnection(connection, client);
                successfulRegistering(sender, connection, nick, username, ircactor);
            } else {
                String username = message.getParameter(0);
                String realname = message.getParameter(1);
                Client client = new Client(username, realname);
                connection.setRegisteringStarted(true);
            }
            //answer

        }
    }

    public static void successfulRegistering(Result<Actor<String>> sender, IRCConnection connection, String nick, String username, Result<Actor<String>> ircactor) {
        sender.forEach(actor -> actor.tell(":" + connection.getServerHost() + " " + Reply.RPL_WELCOME + " " + nick + " :Welcome to the Internet Relay Network " + nick + "!" + username + "@" + connection.getSocket().getInetAddress().getHostName() + "\r\n", ircactor.successValue()));
        sender.forEach(actor -> actor.tell(":" + connection.getServerHost() + " " + Reply.RPL_YOURHOST + " " + nick + ":Your host is " + connection.getServerHost() + ", running version 2.9?\r\n", ircactor.successValue()));
        sender.forEach(actor -> actor.tell(":" + connection.getServerHost() + " " + Reply.RPL_CREATED + " " + nick + ":This server was created " + java.time.LocalDateTime.now().toLocalDate() + "\r\n", ircactor.successValue()));
        sender.forEach(actor -> actor.tell(":" + connection.getServerHost() + " " + Reply.RPL_MYINFO + " " + nick + ":" + connection.getServerHost() + " 2.9 ao mtov" + "\r\n", ircactor.successValue()));

    }
}
