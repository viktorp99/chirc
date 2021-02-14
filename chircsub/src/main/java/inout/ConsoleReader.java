package inout;

import fpinjava.Result;
import fpinjava.Tuple;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class ConsoleReader extends AbstractReader {
    protected ConsoleReader(BufferedReader reader) {
        super(reader);
    }

    @Override
    public Result readLine(String message) {
        //System.out.println("Here CNR readLine");
        //System.out.print(message + " ");
        return readLine();
    }

    @Override
    public Result<Tuple<Integer, Input>> readInt(String message) {
        System.out.print(message + " ");
        return readInt();
    }

    public static ConsoleReader consoleReader() {
        return new ConsoleReader(new BufferedReader(new InputStreamReader(System.in)));
    }
}
