package chirc.IRCCommandHandler;

import actor.Actor;
import chirc.IRCConnection;
import chirc.IRCMessage;
import chirc.IRCServer;
import fpinjava.Result;

public class SendMessage implements Strategy {

    @Override
    public void executeCommand(IRCConnection connection, IRCMessage message, IRCServer server, Result<Actor<String>> sender, Result<Actor<String>> ircactor) {
        String nick = message.getParameter(0);
        String msg = message.getParameter(1);

        if (connection.isRegistered()) {
            System.out.println("message to : " + nick + " data: " + msg);
            IRCConnection con = server.getConnectionByNick(nick);
            if (con != null) {
                con.getWriter().tell(":" + connection.getClient().getNick() + "!" + connection.getClient().getUsername() + "@" + connection.getSocket().getInetAddress().getCanonicalHostName() + " : " + msg + "\r\n", sender.successValue());

            } else {
                // no such nick
            }
        }
    }
}
