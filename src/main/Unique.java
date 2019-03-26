package main;

import org.kohsuke.args4j.Argument;
import org.kohsuke.args4j.Option;

import java.io.*;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

class Unique {

    /** [-i] caseIgnore flag:
     * ignore case of lines while comparing them.
     */
    @Option(name = "-i", usage = "enables case ignore")
    private boolean iActive;

    /** [-u] unique lines mode:
     * output only unique lines (unique for the whole input).
     * Can not be used with count mode flag.
     */
    @Option(name = "-u", usage = "enables unique lines output - does not work with -c flag")
    private boolean uActive;

    /** [-c] count mode:
     * added amount of lines replaced with tht line given.
     */
    @Option(name = "-c", usage = "enables counting of replaced lines")
    private boolean cActive;

    /** [-s N] skip flag:
     * ignore (skip) first N characters while comparing the lines.
     * parameter N can not be negative.
     */
    @Option(name = "-s", usage = "ignore first N symbols")
    private int ignoreNum;

    /** [-o ofile] file output mode:
     * set the output in the file ofile.
     * If ofile can not be reached or this flag is not active - output will be printed in console.
     */
    @Option(name = "-o", usage = "sets the output file")
    private String outName;

    /** [ifile] file input mode:
     * get the input from file ifile.
     * If ifile can not be reached or this flag is not active - input will be received from console.
     * Should be the only one element in arguments due to command line arguments/flags format.
     */
    @Argument
    private ArrayList<String> arguments = new ArrayList<>();

    /**@throws InputMismatchException
     * check command line arguments for correctness.
     * Throws InputMismatchException if [-c] and [-u] are both active at the same time or if
     * [-s N] N is negative.
     */
    public void cmdInputCheck() {
        if (cActive && uActive)
            throw new InputMismatchException("-c and -u flags should not be active at the same time");
        else if (ignoreNum < 0)
            throw new InputMismatchException("-s N error: N should be greater or equal to zero");
    }

    /** getting the input lines with getLines() method: from the file if input file name is given
     * or from console if input file is unreachable or if input file name was not given.
     */
    private ArrayList<String> lines = new ArrayList<>();
    private String cmdGetLine() {
        Scanner in = new Scanner(System.in);
        return in.nextLine();
    }
    private ArrayList<String> getLines() {
        ArrayList<String> tempLines = new ArrayList<>();

        //if [file] field was set
        if (arguments.size() == 1) {
            try {

                FileReader inFR = new FileReader(arguments.get(0));
                BufferedReader bufferedIn = new BufferedReader(inFR);
                bufferedIn.lines().forEach(tempLines::add);
                inFR.close();
                bufferedIn.close();
            } catch (Exception e) {
                e.printStackTrace();
                //if there are problems with reaching the input file we switch to the console input
                System.out.println("Input file name incorrect. " +
                        "Console input mode: type \"uniqstop\" to finish input.");

                //"uniqstop" is the stop command for the end of input
                String cmdIn = "";
                while (!cmdIn.equals("uniqstop")) {
                    cmdIn = cmdGetLine();
                    if (!cmdIn.equals("uniqstop")) tempLines.add(cmdIn);
                }
            }

        }
        //if [file] field was not set - console input
        else {
            System.out.println("console input mode: type \"uniqstop\" to finish input.");

            //"uniqstop" is the stop command for the end of input
            String cmdIn = "";
            while (!cmdIn.equals("uniqstop")) {
                cmdIn = cmdGetLine();
                if (!cmdIn.equals("uniqstop")) tempLines.add(cmdIn);
            }
        }
        return tempLines;
    }

    /**method for lines output: in file, if -o is active,
     * or in console, if output file is unreachable or if -o was not given.
     */
    private void returnLines(ArrayList<String> output) {
        if (outName == null) {
            System.out.println("console output mode:");
            for (String line : output) System.out.println(line);
        } else {
            try {
                BufferedWriter bufferedOut = new BufferedWriter(new FileWriter(outName));
                for (String line : output) {
                    bufferedOut.write(line);
                    bufferedOut.newLine();
                }
                bufferedOut.close();
            } catch (IOException e) {
                System.out.println("Output file name incorrect." +
                        "Console output mode:");
                for (String line : output) System.out.println(line);
            }
        }
    }

    public void run() {
        cmdInputCheck();
        lines = getLines();
        ArrayList<String> out = iterating();
        returnLines(out);
    }

    /** comparing previous and current lines considering [-i] and [-s N] flags. */
    private boolean compare(String prevString, String currString) {
        //caseIgnore flag
        if (iActive) {
            prevString = prevString.toLowerCase();
            currString = currString.toLowerCase();
        }

        //skip first ignoreNum chars
        StringBuilder prevSB = new StringBuilder(prevString).delete(0, ignoreNum);
        StringBuilder currSB = new StringBuilder(currString).delete(0, ignoreNum);

        return prevSB.toString().equals(currSB.toString());
    }

    /** search for copies of the String given - uses compare() method, so it pays attention to the flags given. */
    private boolean isUnique(String line, int index) {
        boolean isUnique = true;
        for (int i = 0; i < lines.size(); i++) {
            if (compare(line, lines.get(i)) && index != i) {
                isUnique = false;
                break;
            }
        }
        return isUnique;
    }

    /** basic method - generating output considering arguments/options given. */
    private ArrayList<String> iterating() {
        ArrayList<String> output = new ArrayList<>();

        //working with lines if they are too small for iterating
        if (lines.size() == 0) return output;
        else if (lines.size() == 1) {
            if (cActive) output.add("1 " + lines.get(0));
            else output.add(lines.get(0));
        }

        //-u flag - if line is unique, add it to the output
        if (uActive) {
            for (int i = 0; i < lines.size(); i++) {
                if (isUnique(lines.get(i), i)) output.add(lines.get(i));
            }
        } else {
            int count = 0;
            String tempLine = lines.get(0);

            for (String line : lines) {
                //if buffered line and current line are the same we need to only increment counter if -c is active
                if (compare(tempLine, line)) {
                    if (cActive) count++;
                }
                //if buffered line and current line are different - we need to output the buffered line and
                //replace it with current one
                else {
                    if (cActive) {
                        output.add(count + " " + tempLine);
                        tempLine = line;
                        count = 1;
                    } else {
                        output.add(tempLine);
                        tempLine = line;
                    }
                }
            }

            //doing stuff with last line
            //if last line and buffered line are the same we only need to output buffered line
            //(with counter if -c is active)
            if (compare(tempLine, lines.get(lines.size() - 1))) {
                if (cActive) output.add(count + " " + tempLine);
                else output.add(tempLine);
            }
            //if these lines are different - we need to output the last one (with proper count = 1 if -c is active)
            else {
                if (cActive) output.add(count + " " + lines.get(lines.size() - 1));
                else output.add(lines.get(lines.size() - 1));
            }

        }
        return output;
    }
}
