package inout;

import list.List;

/**
 * ScriptWriter class that collects output data
 *
 * @author Viktor
 */
public class ScriptWriter implements Output {

    private List<String> output;

    /**
     * Class constructor that initializes the field output to empty
     */
    public ScriptWriter() {
        this.output = List.list();
    }

    /**
     * close method
     */
    @Override
    public void close() throws Exception {
    }

    /**
     * printLine method that collects the given string in a list
     *
     * @param String s - the string to be written
     */
    @Override
    public void printLine(String s) {
        //System.out.println("Here  SCW prtnln");
        //System.err.println(s);

        List<String> ss = List.list(s);
        this.output = List.concat(this.output, ss);
        //System.out.println(ss);
        //System.err.println("3rd" + output);

    }

    /**
     * toList method that returns the list of strings
     *
     * @return output field - the list with the strings
     */
    public List<String> toList() {
        return this.output;
    }

}
