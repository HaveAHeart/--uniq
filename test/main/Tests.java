package main;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.*;
import java.util.InputMismatchException;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class Tests {

    String testDir = "src" + File.separator + "test" + File.separator + "resources";

    private void fileTesting(String[] args, String outName) throws IOException {
        File tempOut = new File("temp.txt");
        Main.main(args);

        FileReader outFR = new FileReader("temp.txt");
        String progOut = new BufferedReader(outFR)
                .lines().collect(Collectors.joining("\n"));
        outFR.close();

        FileReader expectFR = new FileReader(testDir + File.separator + outName);
        String expected = new BufferedReader(expectFR)
                .lines().collect(Collectors.joining("\n"));
        expectFR.close();

        assertEquals(progOut, expected);
        tempOut.delete();
    }

    @Test
    public void exceptionsCheck() {

        //-s N with negative N
        String[] argsExc2 = {"-i", "-c", "-s", "-2"};
        assertThrows(InputMismatchException.class, ()-> Main.main(argsExc2));

        //some random testing file, should throw the CmdLineException anyway
        String[] argsExc3 = {"-i", "-unexisting",
                testDir + File.separator + "inI.txt"};
        Main.main(argsExc3);
    }

    @Test
    public void iTest() throws IOException {
        String[] args = {"-i", "-o", "temp.txt", testDir + File.separator + "inI.txt"};
        fileTesting(args, "outI.txt");
    }

    @Test
    public void sTest() throws IOException {
        String[] args = {"-s", "1", "-o", "temp.txt", testDir + File.separator + "inS.txt"};
        fileTesting(args, "outS.txt");
    }

    @Test
    public void cTest() throws IOException {
        String[] args = {"-c", "-o", "temp.txt", testDir + File.separator + "inC.txt"};
        fileTesting(args, "outC.txt");
    }

    @Test
    public void uTest() throws IOException {
        String[] args = {"-u", "-o", "temp.txt", testDir + File.separator + "inU.txt"};
        fileTesting(args, "outU.txt");
    }

    @Test
    public void icsTest() throws IOException {
        String[] args = {"-i", "-c", "-s", "1", "-o", "temp.txt", testDir + File.separator + "inICS.txt"};
        fileTesting(args, "outICS.txt");
    }

    private ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private PrintStream originalOut = System.out;
    private ByteArrayInputStream inContent;

    @Before
    public void setUpStreams() {
        String input = "uniqstop";
        inContent = new ByteArrayInputStream(input.getBytes());
        System.setIn(inContent);
        System.setOut(new PrintStream(outContent));
    }

    @Test
    public void consoleTest() {
        String[] argsCons = {"-i", "-c"};
        Main.main(argsCons);

        String expected = "console input mode: type \"uniqstop\" to finish input." + System.lineSeparator() +
        "console output mode:" + System.lineSeparator();
        String actual = outContent.toString();
        assertEquals(expected, actual);
    }

    @After
    public void restoreStreams() {
        System.setOut(originalOut);
    }

    @Test
    public void oneStringInFileTest() throws IOException {
        String[] args = {"-i", "-o", "temp.txt", testDir + File.separator + "oneLineIn.txt"};
        fileTesting(args, "oneLineOut.txt");
    }
}
