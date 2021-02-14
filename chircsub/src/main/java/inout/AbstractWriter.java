package inout;

import java.io.PrintWriter;

/**
 * AbstractWriter class that implements the Output interface Here we can print
 * on the PrintWriter
 *
 * @author Viktor
 */
public class AbstractWriter implements Output {
    protected final PrintWriter writer;

    /**
     * Constructor of the class that initializes the PrintWriter field
     *
     * @param writer
     */
    protected AbstractWriter(PrintWriter writer) {
        this.writer = writer;
    }

    /**
     * close method
     */
    @Override
    public void close() throws Exception {
        writer.close();
    }

    /**
     * printLine method that prints in case of no error with the PrintWriter
     */
    @Override
    public void printLine(String s) {
        writer.println(s);

    }

}
