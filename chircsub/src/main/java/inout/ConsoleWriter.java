package inout;

import java.io.PrintWriter;

/**
 * ConsoleWriter class that extends the AbstractWriter class
 * Here its implemented the standard output on the console
 *
 * @author Viktor
 */
public class ConsoleWriter extends AbstractWriter {

    /**
     * Constructor of the class that uses the super constructor
     *
     * @param reader - the writer
     */
    protected ConsoleWriter(PrintWriter writer) {
        super(new PrintWriter(writer, true));
    }

    /**
     * static factory method that returns new object of the class
     *
     * @return new Writer, that prints on the Standard output
     */
    public static ConsoleWriter consoleWriter() {
        return new ConsoleWriter(new PrintWriter(System.out));
    }
}
