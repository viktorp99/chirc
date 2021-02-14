package chirc.IRCCommandHandler;

import actor.Actor;
import chirc.Client;
import chirc.IRCConnection;
import chirc.IRCMessage;
import chirc.IRCServer;
import fpinjava.Result;

public class RegisterNick implements Strategy {
    @Override
    public void executeCommand(IRCConnection connection, IRCMessage message, IRCServer server, Result<Actor<String>> sender, Result<Actor<String>> ircactor) {

        String nick = message.getParameter(0);
        if (nick.equals("")) {
            sender.forEach(actor -> actor.tell(":" + connection.getServerHost() + " " + Reply.ERR_NONICKNAMEGIVEN + " " + " :Nickname parameter expected for the command isn't found\r\n", ircactor.successValue()));
            return;
            // no nickname given
        }
        synchronized (this) {
            if (server.getNicks().contains(nick)) { // if the nick is already used
                Client client = new Client();
                connection.setClient(client);
                sender.forEach(actor -> actor.tell(":" + connection.getServerHost() + " " + Reply.ERR_NICKNAMEINUSE + " " + client.getNick() + " :Nickname is already in use\r\n", ircactor.successValue()));

            } else { // if nick is valid and free of use
                if (!connection.isRegisteringStarted()) {
                    Client client = new Client(nick);
                    connection.setClient(client);
                    connection.setRegisteringStarted(true);
                } else {
                    connection.getClient().setNick(nick);
                    if (!connection.isRegistered()) {
                        server.registerConnection(connection, connection.getClient());
                        RegisterUser.successfulRegistering(sender, connection, connection.getClient().getNick(), connection.getClient().getUsername(), ircactor);
                    }

                }
                //sender.forEach(actor -> actor.tell("Succesfull registered nick",ircactor.successValue()));

            }
        }
    }

}
