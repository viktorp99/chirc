package chirc;

import java.util.ArrayList;

class IRCParser {

    static IRCMessage parse(String commands) {

        String[] words = commands.split(" ");

        if (words.length <= 0) {
            System.out.println("No command params");
            return null;
        }

        String command = words[0]; // command for NICK, USER
        ArrayList<String> params = new ArrayList<String>();
        params = paramDump(words);


        return new IRCMessage(command, commands, params);
    }

    private static ArrayList<String> paramDump(String[] params) {
        ArrayList<String> res = new ArrayList<String>();
        for (int i = 1; i < params.length; i++) {
            if (params[i].charAt(0) == ':') {
                String s = params[i].substring(1);
                for (int j = i + 1; j < params.length; j++) {
                    s = s.concat(" " + params[j]);
                }
                res.add(s);
                break;
            }
            res.add(params[i]);
        }
        return res;
    }

}
