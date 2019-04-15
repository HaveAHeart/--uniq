package main;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.InputMismatchException;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class Tests {

    private void fileTesting(String[] args, String outName) throws IOException {

        Path outDir = Paths.get("src","test","resources", outName);

        File tempOut = new File("temp.txt");
        Main.main(args);

        FileReader outFR = new FileReader("temp.txt");
        String progOut = new BufferedReader(outFR)
                .lines().collect(Collectors.joining(System.lineSeparator()));
        outFR.close();

        FileReader expectFR = new FileReader(outDir.toString());
        String expected = new BufferedReader(expectFR)
                .lines().collect(Collectors.joining(System.lineSeparator()));
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
        String[] argsExc3 = {"-i", "-unexisting", "test\\main\\inI.txt"};
        Main.main(argsExc3);
    }

    @Test
    public void iTest() throws IOException {
        Path testDir = Paths.get("src","test","resources", "inI.txt");
        String[] args = {"-i", "-o", "temp.txt", testDir.toString()};
        fileTesting(args, "outI.txt");
    }

    @Test
    public void sTest() throws IOException {
        Path testDir = Paths.get("src","test","resources", "inS.txt");
        String[] args = {"-s", "1", "-o", "temp.txt", testDir.toString()};
        fileTesting(args, "outS.txt");
    }

    @Test
    public void cTest() throws IOException {
        Path testDir = Paths.get("src","test","resources", "inC.txt");
        String[] args = {"-c", "-o", "temp.txt", testDir.toString()};
        fileTesting(args, "outC.txt");
    }

    @Test
    public void uTest() throws IOException {
        Path testDir = Paths.get("src","test","resources", "inU.txt");
        String[] args = {"-u", "-o", "temp.txt", testDir.toString()};
        fileTesting(args, "outU.txt");
    }

    @Test
    public void icsTest() throws IOException {
        File tempOut = new File("temp.txt");
        Path testDir = Paths.get("src","test","resources", "inICS.txt");
        String[] args = {"-i", "-c", "-s", "1", "-o", "temp.txt", testDir.toString()};
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
        Path testDir = Paths.get("src","test","resources", "oneLineIn.txt");
        String[] args = {"-i", "-o", "temp.txt", testDir.toString()};
        fileTesting(args, "oneLineOut.txt");
    }
}
