package inout;

import fpinjava.Result;
import fpinjava.Tuple;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;

public class UDPSocket implements InputOutput {

    private final int BUFFSIZE = 508;
    private DatagramSocket socket;
    private InetAddress addr;
    private int port;
    private boolean inputClosed = false;
    private boolean outputClosed = false;

    private UDPSocket(String addr, int port) {
        try {
            this.addr = InetAddress.getByName(addr);
            this.socket = new DatagramSocket();
        } catch (Exception e) {
            e.printStackTrace();
        }

        this.port = port;
    }

    private UDPSocket(int port) {
        try {

            this.addr = InetAddress.getByName("localhost");
            this.socket = new DatagramSocket(port, this.addr);
        } catch (Exception e) {
            e.printStackTrace();
        }

        this.port = port;
    }

    public static Input udpSockReader(int localPort) {
        UDPSocket udpSocket = new UDPSocket(localPort);
        udpSocket.outputClosed = true;
        return udpSocket;
    }

    public static Output udpSockWriter(String remoteHost, int remotePort) {
        UDPSocket udpSocket = new UDPSocket(remoteHost, remotePort);
        udpSocket.inputClosed = true;
        return udpSocket;
    }

    public static UDPSocket udpSockReaderWriter(int localPort) {
        return new UDPSocket(localPort);
    }

    public static InputOutput udpSockReaderWriter(String remoteHost, int remotePort) {
        return new UDPSocket(remoteHost, remotePort);
    }

    private void send(String s) {
        byte[] data = s.getBytes();
        DatagramPacket packet = new DatagramPacket(data, data.length, addr, port);
        //System.out.println(port + " " + addr);
        try {
            socket.send(packet);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String receive() {

        String res;
        DatagramPacket packet = new DatagramPacket(new byte[BUFFSIZE], BUFFSIZE);
        try {
            this.socket.receive(packet);
            this.addr = packet.getAddress();
            this.port = packet.getPort();
        } catch (IOException e) {
            e.printStackTrace();
        }
        res = new String(packet.getData(), 0, packet.getLength());


        //System.out.println("+" + res + "+");
        return res;

    }

    @Override
    public Result<Tuple<String, Input>> readLine() {

        try {
            String msg = receive();
            if (msg.equals("\u0004")) {
                shutdownInput();
                return Result.empty();
            } else {
                return Result.success(new Tuple<>(msg, this));
            }

        } catch (Exception e) {
            return Result.failure(e);
        }
    }

    @Override
    public Result<Tuple<Integer, Input>> readInt() {
        return null;
    }

    @Override
    public void printLine(String s) {
        send(s);
    }

    @Override
    public void shutdownOutput() {
        outputClosed = true;
        send("\u0004");
        if (inputClosed)
            close();
    }

    @Override
    public void shutdownInput() {
        inputClosed = true;
        if (outputClosed)
            close();
    }

    @Override
    public Socket getSocket() {
        return null;
    }

    @Override
    public void close() {
        this.socket.close();
    }

    public String toString() {
        return addr + " " + port;
    }
}
