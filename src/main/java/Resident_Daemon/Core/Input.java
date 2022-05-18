package Resident_Daemon.Core;

import java.io.ByteArrayInputStream;
import java.util.Scanner;

public class Input {
    private static Scanner scanner;

    public static void setIn(String input) {
        System.setIn(new ByteArrayInputStream(input.getBytes()));
    }
    public static void confScanner() {
        scanner = new Scanner(System.in);
    }
    public static String nextLine() {
        return scanner.nextLine();
    }
}
