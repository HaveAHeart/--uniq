package main;

import org.junit.Test;
import sun.misc.IOUtils;

import java.io.*;
import java.nio.charset.StandardCharsets;
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

        String[] argsExc3 = {"-i", "-unexisting"};

    }

    @Test
    public void iTest() {
        File tempOut = new File("temp.txt");
        String[] argsN = {"-i", "-o", "temp.txt", "C:\\Users\\Alexey\\IdeaProjects\\Uniq\\test\\main\\inI.txt"};
        Main.main(argsN);
        try {
            FileReader outFR = new FileReader("temp.txt");
            String progOut = new BufferedReader(outFR)
                    .lines().collect(Collectors.joining("\n"));
            outFR.close();

            FileReader expectFR = new FileReader("C:\\Users\\Alexey\\IdeaProjects\\Uniq\\test\\main\\outI.txt");
            String expected = new BufferedReader(expectFR)
                    .lines().collect(Collectors.joining("\n"));
            expectFR.close();
            System.out.println(progOut);
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
        String[] argsS = {"-s", "1", "-o", "temp.txt", "C:\\Users\\Alexey\\IdeaProjects\\Uniq\\test\\main\\inS.txt"};
        Main.main(argsS);
        try {
            FileReader outFR = new FileReader("temp.txt");
            String progOut = new BufferedReader(outFR)
                    .lines().collect(Collectors.joining("\n"));
            outFR.close();

            FileReader expectFR = new FileReader("C:\\Users\\Alexey\\IdeaProjects\\Uniq\\test\\main\\outS.txt");
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
        String[] argsC = {"-c", "-o", "temp.txt", "C:\\Users\\Alexey\\IdeaProjects\\Uniq\\test\\main\\inC.txt"};
        Main.main(argsC);
        try {
            FileReader outFR = new FileReader("temp.txt");
            String progOut = new BufferedReader(outFR)
                    .lines().collect(Collectors.joining("\n"));
            outFR.close();

            FileReader expectFR = new FileReader("C:\\Users\\Alexey\\IdeaProjects\\Uniq\\test\\main\\outC.txt");
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
    public void icsTest() {
        File tempOut = new File("temp.txt");
        String[] argsICS =
                {"-i", "-c", "-s", "1", "-o", "temp.txt", "C:\\Users\\Alexey\\IdeaProjects\\Uniq\\test\\main\\inICS.txt"};
        Main.main(argsICS);
        try {
            FileReader outFR = new FileReader("temp.txt");
            String progOut = new BufferedReader(outFR)
                    .lines().collect(Collectors.joining("\n"));
            outFR.close();

            FileReader expectFR = new FileReader("C:\\Users\\Alexey\\IdeaProjects\\Uniq\\test\\main\\outICS.txt");
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

}
