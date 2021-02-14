package chirc;

public class Client {

    private String nick;
    private String user;
    private String realname;


    public Client(String nick, String user, String realname) {
        this.nick = nick;
        this.user = user;
        this.realname = realname;
    }

    public Client(String nick) {
        this.nick = nick;
        this.user = "";
        this.realname = "";
    }

    public Client(String user, String realname) {
        this.nick = "*";
        this.user = user;
        this.realname = realname;
    }

    public Client() {
        this.nick = "*";
        this.user = "";
        this.realname = "";
    }


    public void setNick(String nick) {
        this.nick = nick;
    }

    public String getNick() {
        return this.nick;
    }

    public String getUsername() {
        return this.user;
    }

}
