package inout;

public interface Output extends AutoCloseable {

    void printLine(String s);

    default void shutdownOutput() {
        try {
            close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}