package Resident_Daemon.Core;

import Resident_Daemon.CommandsPack.CommandExecutor;
import Resident_Daemon.ConsoleMenu;
import Resident_Daemon.PackageOS.DetectOS;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Main {
    private Singleton mainData;
    CommandExecutor commandExecutor;

    public Main() {
        commandExecutor = new CommandExecutor();

        mainData = Singleton.getSingletonObject();
        mainData.setOperatingSystem(new DetectOS().getOperatingSystem());

    }

    private static void createChild() {
//        try
//        {
//            // create a new process
//            System.out.println("Creating Process");

//            String command = "cmd.exe";

//            String command = "..\\..\\..\\createChild.bat";
            String command = "createChild.bat";
//            String command ="java -jar C:\\Users\\balan\\IdeaProjects\\P2P-FileSync\\out\\artifacts\\P2P_FileSync_jar\\P2P-FileSync.jar 1";

            Runtime rt = Runtime.getRuntime();
        try {
            Process pr = rt.exec(command);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    private static void runTestBalan() {
        System.out.println("runTestBalan");

        createChild();
    }

    private static void createFile() {
//        String TEXT_FILE = "createChild.bat";

//        ----------
//        final String TEXT_FILE = "..\\..\\..\\pipe.txt";
        final String TEXT_FILE = System.getProperty("user.dir") + "\\pipe.txt";

        Path path = Paths.get(TEXT_FILE);

        String question = "acd";

        try {
            Files.write(path, question.getBytes());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

//        String TEXT_FILE = "abcd.txt";
//
//        Path textFilePath = Paths.get(TEXT_FILE);
//        try {
//            Files.createFile(textFilePath);
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
    }

    public static void main(String[] args) {

        if (args.length > 0) {
            System.out.println("abc");

            System.out.println(args[0]);
//            Singleton.port = 0;
            Singleton.port = 30004;
            System.out.println("Proces copil");

            createFile();
        }

        System.out.println("Working Directory = " + System.getProperty("user.dir"));



//        Main main = new Main();
//        CommandExecutor commandExecutor = main.commandExecutor;
//
//        System.out.println("App started! System detected: " + main.mainData.getOperatingSystem());


//        ConsoleMenu.startToInteractWithTheUser()

        if (args.length == 0) {
            System.out.println("Proces tata");
            runTestBalan();
        }
    }
}
