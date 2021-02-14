package inout;

import fpinjava.Result;
import fpinjava.Tuple;
import stream.Stream;

public interface Input extends AutoCloseable {

    Result readLine();

    Result<Tuple<Integer, Input>> readInt();

    default Stream<String> lines() {
        return Stream.unfold(this, Input::readLine);
    }

    default Stream<Integer> ints() {
        return Stream.unfold(this, Input::readInt);
    }

    default Result readLine(String message) {
        //System.out.println("Here INPUT readLine");
        return readLine();
    }

    default Result<Tuple<Integer, Input>> readInt(String message) {
        return readInt();
    }

    default void shutdownInput() {
        try {
            close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}