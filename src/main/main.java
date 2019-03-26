package main;

import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;

class Main {
    /**
     * command line program for doing some stuff with unique and repeating strings
     * format: uniq [-i] [-u] [-c] [-s num] [-o ofile] [file]
     * replacing identical consecutive strings with only one.
     * e.g.: ["foo", "bar", "bar", "foo"] will return ["foo", "bar", "foo"]
     * flags:
     * -i - ignore case while comparing strings
     * -u - return only original strings(do not work with -c flag)
     * -c - return amount of identical lines replaced before returning the line
     * -s num - ignore first num symbols while comparing strings
     * -o ofile - print the output in output file ofile. If it is not given - output in console
     * file - get the lines from input file file. If it is not given - input in console
     */


    public static void main(String[] args) {

        //parsing arguments
        Unique bean = new Unique();
        CmdLineParser parser = new CmdLineParser(bean);

        try {
            parser.parseArgument(args);
        } catch(CmdLineException e) {
            System.err.println(e.getMessage());
            parser.printUsage(System.err);
        }

        bean.run();
    }
}

