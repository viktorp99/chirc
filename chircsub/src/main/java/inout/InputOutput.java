package inout;

import chirc.IRCConnection;
import fpinjava.Result;

import java.net.Socket;

public interface InputOutput extends Input, Output {

    Result readLine();

    void printLine(String s);

    void shutdownOutput();

    void shutdownInput();

    Socket getSocket();
}
