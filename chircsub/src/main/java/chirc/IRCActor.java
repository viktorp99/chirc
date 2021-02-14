package chirc;

import actor.AbstractActor;
import actor.Actor;
import chirc.IRCCommandHandler.*;
import fpinjava.Result;

public class IRCActor extends AbstractActor<String> {


    IRCParser parser;
    IRCMessage msg;
    IRCServer server;

    public IRCActor(IRCServer server) {
        super("EchoActor", Actor.Type.SERIAL);
        parser = new IRCParser();
        this.server = server;
    }


    @Override
    public synchronized void onReceive(String message, Result<Actor<String>> sender) {
        System.err.println(message);
        msg = IRCParser.parse(message);

        Context context = null;
        if (msg.getType().equals(IRCMessage.COMMAND_TYPE.NICK)) {
            context = new Context(new RegisterNick());
            context.executeStrategy(sender.successValue().getConnection(), msg, server, sender, Result.success(this));
        } else if (msg.getType().equals(IRCMessage.COMMAND_TYPE.USER)) {
            context = new Context(new RegisterUser());
            context.executeStrategy(sender.successValue().getConnection(), msg, server, sender, Result.success(this));
        } else if (!sender.successValue().getConnection().isRegistered()) {
            if (msg.getType().equals(IRCMessage.COMMAND_TYPE.ERR))
                sender.forEach(actor -> actor.tell(":" + sender.successValue().getConnection().getServerHost() + " " + Reply.ERR_NOTREGISTERED + ":Client must be registered before server allows parsing in detail."));
        } else if (msg.getType().equals(IRCMessage.COMMAND_TYPE.PRIVMSG)) {
            context = new Context(new SendMessage());
            context.executeStrategy(sender.successValue().getConnection(), msg, server, sender, Result.success(this));
        } else if (msg.getType().equals(IRCMessage.COMMAND_TYPE.PING)) {
            context = new Context(new Ping());
            context.executeStrategy(sender.successValue().getConnection(), msg, server, sender, Result.success(this));
        } else if (msg.getType().equals(IRCMessage.COMMAND_TYPE.QUIT)) {
            context = new Context(new Quit());
            context.executeStrategy(sender.successValue().getConnection(), msg, server, sender, Result.success(this));
        } else if (msg.getType().equals(IRCMessage.COMMAND_TYPE.ERR)) {
            sender.forEach(actor -> actor.tell(": " + sender.successValue().getConnection().getServerHost() + " " + Reply.ERR_UNKNOWNCOMMAND + msg.getCommand() + ": Unknown command", Result.success(this)));
        }


    }

    @Override
    public IRCConnection getConnection() {
        return null;
    }
}
