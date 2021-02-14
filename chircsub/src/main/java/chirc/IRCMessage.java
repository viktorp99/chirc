package chirc;

import java.util.ArrayList;

public class IRCMessage {

    private String message;
    private String command;
    private ArrayList<String> parameters;
    private COMMAND_TYPE type;

    enum COMMAND_TYPE {
        USER, NICK, QUIT, PRIVMSG, PING, PONG, ERR
    }

    IRCMessage(String command, String message, ArrayList<String> parameters) {
        System.out.println("command:" + command);

        this.command = command;
        switch (command.toUpperCase()) {
            case "NICK":
                type = COMMAND_TYPE.NICK;
                break;
            case "USER":
                type = COMMAND_TYPE.USER;
                break;
            case "PRIVMSG":
                type = COMMAND_TYPE.PRIVMSG;
                break;
            case "PING":
                type = COMMAND_TYPE.PING;
                break;
            case "QUIT":
                type = COMMAND_TYPE.QUIT;
                break;
            case "PONG":
                type = COMMAND_TYPE.PONG;
                break;
            default:
                type = COMMAND_TYPE.ERR;
        }
        this.message = message;
        this.parameters = parameters;

    }

    public static boolean validateParams(IRCMessage message, int numberOfParam) {
        return message.getParameters().size() >= numberOfParam;
    }

    public COMMAND_TYPE getType() {
        return this.type;
    }

    String getCommand() {
        return this.command;
    }

    public String getMessage() {
        return this.message;
    }

    public ArrayList<String> getParameters() {
        return this.parameters;
    }

    public String getParameter(int i) {
        return this.parameters.get(i);
    }

}
