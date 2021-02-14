package inout;

import chirc.IRCConnection;
import fpinjava.Result;
import fpinjava.Tuple;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class TCPSocket implements InputOutput {

    private Socket socket;
    private AbstractReader reader;
    private AbstractWriter writer;
    private IRCConnection connection;

    private TCPSocket(String host, int port) {
        try {
            this.socket = new Socket(host, port);
            reader = new AbstractReader(new BufferedReader(
                    new InputStreamReader(socket.getInputStream())));
            writer = new AbstractWriter(new PrintWriter(socket.getOutputStream(), true));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private TCPSocket(int port) {
        try {
            ServerSocket server = new ServerSocket(port);
            this.socket = server.accept();
            this.reader = new AbstractReader(new BufferedReader(
                    new InputStreamReader(this.socket.getInputStream())));
            this.writer = new AbstractWriter(new PrintWriter(this.socket.getOutputStream(), true));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private TCPSocket(ServerSocket sock) {
        try {
            this.socket = sock.accept();
            this.reader = new AbstractReader(new BufferedReader(
                    new InputStreamReader(this.socket.getInputStream())));
            this.writer = new AbstractWriter(new PrintWriter(this.socket.getOutputStream(), true));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    @Override
    public Result<Tuple<String, Input>> readLine() {
        return reader.readLine();
    }

    @Override
    public Result<Tuple<Integer, Input>> readInt() {
        return reader.readInt();
    }

    @Override
    public void printLine(String s) {
        writer.printLine(s);
    }

    @Override
    public void shutdownOutput() {
        try {
            socket.shutdownOutput();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void shutdownInput() {
        try {
            socket.shutdownInput();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void close() throws Exception {
        socket.close();
    }

    public static Input tcpSockReader(int localPort) {
        TCPSocket sock = new TCPSocket(localPort);
        sock.shutdownOutput();
        return sock;
    }

    public static Output tcpSockWriter(String remoteHost, int remotePort) {
        TCPSocket sock = new TCPSocket(remoteHost, remotePort);
        sock.shutdownInput();
        return sock;
    }

    public static InputOutput tcpSockReaderWriter(int localPort) {
        return new TCPSocket(localPort);
    }

    public static InputOutput tcpSockReaderWriter(ServerSocket sock) {
        return new TCPSocket(sock);
    }

    public static InputOutput tcpSockReaderWriter(String remoteHost, int remotePort) {
        return new TCPSocket(remoteHost, remotePort);
    }

    public Socket getSocket() {
        return this.socket;
    }
}
