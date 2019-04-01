package main;

import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;

class Main {
    /**
     * @author Alexey Bedrin
     * --uniq - command line program for doing some stuff with unique and repeating strings
     * format: uniq [-i] [-u] [-c] [-s num] [-o ofile] [ifile]
     * replacing identical consecutive strings with only one.
     * e.g.: ["foo", "bar", "bar", "foo"] will return ["foo", "bar", "foo"]
     * flags:
     * @see [-i] flag and iActive boolean field in Unique class.
     * @see [-u] flag and uActive boolean field in Unique class.
     * @see [-c] flag and cActive boolean field in Unique class.
     * @see [-s N] flag and int ignoreNum field in Unique class.
     * @see [-o ofile] flag and String outName field in Unique class.
     * @see [ifile] flag and arguments field in Unique class.
     */


    public static void main(String[] args) {
        //parsing arguments
        Unique temp = new Unique();
        CmdLineParser parser = new CmdLineParser(temp);

        try {
            parser.parseArgument(args);
            temp.run();
        }
        catch(CmdLineException e) {
            System.err.println(e.getMessage());
            parser.printUsage(System.err);
        }
    }
}

