package chirc;

import actor.Writer;

import java.net.Socket;

public class IRCConnection {

    private Socket socket;
    private IRCServer server;
    private Client client;
    private boolean registered;
    private boolean registeringStarted;
    private String serverHost;
    private Writer writer;

    public IRCConnection(Socket socket, IRCServer server, Writer writer) {
        this.socket = socket;
        this.server = server;
        client = new Client();
        this.registered = false;
        this.registeringStarted = false;
        serverHost = server.getServerSocket().getInetAddress().getHostName();
        this.writer = writer;
    }

    public boolean isRegisteringStarted() {
        return registeringStarted;
    }

    public void setRegisteringStarted(boolean registeringStarted) {
        this.registeringStarted = registeringStarted;
    }

    public void setNick(String nick) {
        this.client.setNick(nick);
        registeringStarted = true;
    }

    public void register(Client client) {
        this.client = client;
        this.registered = true;
    }

    public Socket getSocket() {
        return socket;
    }

    public IRCServer getServer() {
        return server;
    }

    public void setServer(IRCServer server) {
        this.server = server;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public boolean isRegistered() {
        return registered;
    }

    public String getServerHost() {
        return serverHost;
    }

    public Writer getWriter() {
        return writer;
    }

    public void setWriter(Writer writer) {
        this.writer = writer;
    }
}
