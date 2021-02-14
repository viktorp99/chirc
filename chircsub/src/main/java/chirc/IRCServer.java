package chirc;

import actor.ActorSystem;

import java.net.ServerSocket;
import java.util.ArrayList;

public class IRCServer {

    ArrayList<IRCConnection> connections;
    ArrayList<IRCConnection> registeredConnections;
    ArrayList<String> nicks;


    ServerSocket serverSocket;


    public IRCServer() {
        // serverSocket = TCPSocket.tcpSockReaderWriter(8080);


        connections = new ArrayList<IRCConnection>();
        registeredConnections = new ArrayList<IRCConnection>();
        nicks = new ArrayList<String>();
        serverSocket = null;
    }


    void listen() {
        IRCActor actor = new IRCActor(this);
        Conn r = ActorSystem.publish2multiple(actor, 8080, this);
        r.run();
        //IRCConnection con = new IRCConnection(r.getSocket(),this,r.getWriter());
        System.out.println("now");
    }


    public synchronized void closeConnection(IRCConnection r) {
        this.connections.remove(r);
        this.nicks.remove(r.getClient().getNick());
        this.registeredConnections.remove(r);
    }

    public synchronized void registerConnection(IRCConnection r, Client client) {
        this.registeredConnections.add(r);
        this.nicks.add(r.getClient().getNick());
        this.connections.remove(r);
        r.register(client);

        System.out.println("Successfull registered user");

    }

    public synchronized ArrayList<String> getNicks() {
        return nicks;
    }

    public synchronized void addConnection(IRCConnection connection) {
        this.connections.add(connection);
    }

    public synchronized IRCConnection getConnectionByNick(String nick) {
        System.out.println("connection.lenght = " + registeredConnections.size());
        for (IRCConnection con : registeredConnections) {
            if (con.getClient().getNick().toLowerCase().equals(nick.toLowerCase()))
                return con;
        }
        return null;
    }


    ServerSocket getServerSocket() {
        return serverSocket;
    }

    public void setServerSocket(ServerSocket serverSocket) {
        this.serverSocket = serverSocket;
    }
}


