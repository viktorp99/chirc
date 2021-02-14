package actor;

import chirc.Conn;
import chirc.IRCConnection;
import chirc.IRCServer;
import fpinjava.Result;
import inout.InputOutput;
import inout.TCPSocket;
import inout.UDPSocket;

import java.io.IOException;
import java.net.ServerSocket;

public class ActorSystem {

    public static Runnable publish2one(Actor<String> actor, int port, boolean udp) {
        InputOutput io;
        if (udp) {
            io = UDPSocket.udpSockReaderWriter(port);
        } else {
            io = TCPSocket.tcpSockReaderWriter(port);
        }
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                Writer wr = new Writer(io, io, true);
                wr.start(Result.success(actor));
                //System.out.println(actor.toString());
            }
        };
        return runnable;
    }

    public static Conn publish2multiple(Actor<String> actor, int port, IRCServer serv) {

        try {
            ServerSocket server = new ServerSocket(port);
            serv.setServerSocket(server);
            Conn runnable = new Conn() {
                InputOutput io = null;
                Writer wr = null;

                @Override
                public Writer getWriter() {
                    return wr;
                }

                @Override
                public void run() {
                    while (true) {
                        io = TCPSocket.tcpSockReaderWriter(server);
                        wr = new Writer(io, io, true);
                        wr.start(Result.success(actor));
                        IRCConnection con = new IRCConnection(io.getSocket(), serv, wr);
                        serv.addConnection(con);
                        wr.setConnection(con);
                    }
                }
            };
            return runnable;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }


    public static Writer actorSelection(String host, int port, boolean udp) {
        InputOutput io;
        if (udp) {
            io = UDPSocket.udpSockReaderWriter(host, port);
        } else {
            io = TCPSocket.tcpSockReaderWriter(host, port);
        }
        return new Writer(io, io, true);
    }
}
