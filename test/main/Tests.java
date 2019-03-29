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

    @Test
    public void exceptionsCheck() {
        //-c and -u at the same time
        String[] argsExc1 = {"-i", "-c", "-u"};
        assertThrows(InputMismatchException.class, ()-> Main.main(argsExc1));

        //-s N with negative N
        String[] argsExc2 = {"-i", "-c", "-s", "-2"};
        assertThrows(InputMismatchException.class, ()-> Main.main(argsExc2));

        //some random testing file, should throw the CmdLineException anyway
        String[] argsExc3 = {"-i", "-unexisting", "test\\main\\inI.txt"};
        Main.main(argsExc3);
    }

    @Test
    public void iTest() {
        File tempOut = new File("temp.txt");
        String[] argsN = {"-i", "-o", "temp.txt", "test\\main\\inI.txt"};
        Main.main(argsN);
        try {
            FileReader outFR = new FileReader("temp.txt");
            String progOut = new BufferedReader(outFR)
                    .lines().collect(Collectors.joining("\n"));
            outFR.close();

            FileReader expectFR = new FileReader("test\\main\\outI.txt");
            String expected = new BufferedReader(expectFR)
                    .lines().collect(Collectors.joining("\n"));
            expectFR.close();
            assertEquals(progOut, expected);

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("output file problems in nTest");
        }
        tempOut.delete();
    }

    @Test
    public void sTest() {
        File tempOut = new File("temp.txt");
        String[] argsS = {"-s", "1", "-o", "temp.txt", "test\\main\\inS.txt"};
        Main.main(argsS);
        try {
            FileReader outFR = new FileReader("temp.txt");
            String progOut = new BufferedReader(outFR)
                    .lines().collect(Collectors.joining("\n"));
            outFR.close();

            FileReader expectFR = new FileReader("test\\main\\outS.txt");
            String expected = new BufferedReader(expectFR)
                    .lines().collect(Collectors.joining("\n"));
            expectFR.close();

            assertEquals(progOut, expected);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("output file problems in sTest");
        }
        tempOut.delete();
    }

    @Test
    public void cTest() {
        File tempOut = new File("temp.txt");
        String[] argsC = {"-c", "-o", "temp.txt", "test\\main\\inC.txt"};
        Main.main(argsC);
        try {
            FileReader outFR = new FileReader("temp.txt");
            String progOut = new BufferedReader(outFR)
                    .lines().collect(Collectors.joining("\n"));
            outFR.close();

            FileReader expectFR = new FileReader("test\\main\\outC.txt");
            String expected = new BufferedReader(expectFR)
                    .lines().collect(Collectors.joining("\n"));
            expectFR.close();

            assertEquals(progOut, expected);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("output file problems in cTest");
        }
        tempOut.delete();
    }

    @Test
    public void uTest() {
        File tempOut = new File("temp.txt");
        String[] argsU = {"-u", "-o", "temp.txt", "test\\main\\inU.txt"};
        Main.main(argsU);
        try {
            FileReader outFR = new FileReader("temp.txt");
            String progOut = new BufferedReader(outFR)
                    .lines().collect(Collectors.joining("\n"));
            outFR.close();

            FileReader expectFR = new FileReader("test\\main\\outU.txt");
            String expected = new BufferedReader(expectFR)
                    .lines().collect(Collectors.joining("\n"));
            expectFR.close();

            assertEquals(progOut, expected);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("output file problems in uTest");
        }
        tempOut.delete();
    }

    @Test
    public void icsTest() {
        File tempOut = new File("temp.txt");
        String[] argsICS =
                {"-i", "-c", "-s", "1", "-o", "temp.txt", "test\\main\\inICS.txt"};
        Main.main(argsICS);
        try {
            FileReader outFR = new FileReader("temp.txt");
            String progOut = new BufferedReader(outFR)
                    .lines().collect(Collectors.joining("\n"));
            outFR.close();

            FileReader expectFR = new FileReader("test\\main\\outICS.txt");
            String expected = new BufferedReader(expectFR)
                    .lines().collect(Collectors.joining("\n"));
            expectFR.close();

            assertEquals(progOut, expected);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("output file problems in icsTest");
        }
        tempOut.delete();
    }

    private ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private PrintStream originalOut = System.out;
    private ByteArrayInputStream inContent;

    @Before
    public void setUpStreams() {
        inContent = new ByteArrayInputStream("uniqstop".getBytes());
        System.setIn(inContent);
        System.setOut(new PrintStream(outContent));
    }

    @Test
    public void consoleTest() {
        String[] argsCons = {"-i", "-c"};
        Main.main(argsCons);

        String expected = "console input mode: type \"uniqstop\" to finish input.\r\nconsole output mode:\r\n";
        String actual = outContent.toString();
        assertEquals(expected, actual);
    }

    @After
    public void restoreStreams() {
        System.setOut(originalOut);

    }
}
