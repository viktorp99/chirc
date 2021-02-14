package chirc;

public class Main {

    public static void main(String[] args){

        System.out.println("Starting server...");
        IRCServer server = new IRCServer();
        server.listen();
        System.out.println("Closing server...");
    }
}

