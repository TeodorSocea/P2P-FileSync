package Resident_Daemon.Core;

import Resident_Daemon.Utils.BasicFileUtils;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class Input {
    private static Scanner scanner;

//    I want to read from a file three values separated with spaces

    public static void setIn(String input) {
        System.setIn(new ByteArrayInputStream(input.getBytes()));
    }

    public static String nextString() {

        if (!scanner.hasNext())
            return "";

        return scanner.next();
    }

    public static Boolean nextBoolean() {

        String str = Input.nextString();

        if (!("true".equals(str) || "false".equals(str)))
            throw new NumberFormatException();

        Boolean bool = Boolean.parseBoolean(str);
        return bool;
    }

    public static Long nextLong() {

        String str = Input.nextString();

        long longVal = Long.parseLong(str);

        return longVal;
    }

    public static Integer nextInteger() {

        String str = Input.nextString();

        Integer integerVal = Integer.parseInt(str);

        return integerVal;
    }

    public static SyncRecord nextSyncRecord() {

        String filePath = Input.nextString();
        boolean isSync = Input.nextBoolean();
        Long timestamp = Input.nextLong();

        return new SyncRecord(filePath, isSync, timestamp);
    }

    /**
     * This method configures the internal scanner to parse the content of a file
     */
    public static void confScanner(String pathToFile) throws FileNotFoundException {

//        check if file is valid
        if (!BasicFileUtils.isValidFile(pathToFile))
            throw new FileNotFoundException();

//        configure the scanner
        File text = new File(pathToFile);
        scanner = new Scanner(text);
    }

    /**
     * This method configures the internal scanner to parse stdin
     */
    public static void confScanner() {
        scanner = new Scanner(System.in);
    }
    public static String nextLine() {
        return scanner.nextLine();
    }
}
