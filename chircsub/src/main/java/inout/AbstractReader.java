package inout;

import fpinjava.Result;
import fpinjava.Tuple;

import java.io.BufferedReader;

public class AbstractReader implements Input {
    protected final BufferedReader reader;

    protected AbstractReader(BufferedReader reader) {
        this.reader = reader;
    }

    /**
     * readline method that reads lines until eof and returns Result of tuple - (String, AbstractReader)
     * @return
     */
    @Override
    public Result<Tuple<String, Input>> readLine() {
        try {
            String s = reader.readLine();
            return s == null ? Result.empty() : Result.success(new Tuple<>(s, this));
        } catch (Exception e) {
            return Result.failure(e);
        }
    }

    @Override
    public Result<Tuple<Integer, Input>> readInt() {
        try {
            String s = reader.readLine();
            return s.length() == 0 ? Result.empty() : Result.success(new Tuple<>(Integer.parseInt(s), this));
        } catch (Exception e) {
            return Result.failure(e);
        }
    }

    @Override
    public void close() throws Exception {
        try {
            System.out.println("AbstractReader closed!");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}