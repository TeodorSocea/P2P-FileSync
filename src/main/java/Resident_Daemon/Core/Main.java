package Resident_Daemon.Core;

import Resident_Daemon.CommandsPack.CommandExecutor;
import Resident_Daemon.MenuPack.ConsoleMenu;
import Resident_Daemon.Utils.BasicFileUtils;
import Resident_Daemon.Utils.GetTextFiles;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class Main {
    private Singleton mainData;
    CommandExecutor commandExecutor;

    public Main() {
        Input.confScanner();
        commandExecutor = new CommandExecutor();

        mainData = Singleton.getSingletonObject();
        mainData.setOperatingSystem(System.getProperty("os.name"));

        try {
            Singleton.loadSingletonData();
        } catch (IOException e) {
            e.printStackTrace();
        }

        SignalReceiver signalReceiver = new SignalReceiver();

        new Thread(signalReceiver).start();
    }

    public static void ResidentDaemonINIT() {
        Singleton mainData;

        Input.confScanner();

        mainData = Singleton.getSingletonObject();
        mainData.setOperatingSystem(System.getProperty("os.name"));

        try {
            Singleton.loadSingletonData();
        } catch (IOException e) {
            e.printStackTrace();
        }

        SignalReceiver signalReceiver = new SignalReceiver();

        new Thread(signalReceiver).start();
    }

    public static void testSerialization() {

        try {

            Singleton.getSingletonObject().setFolderToSyncPath("/downloads");
            Singleton.saveSingletonData();

            Singleton.loadSingletonData();
            System.out.println(Singleton.getSingletonObject().getFolderToSyncPath());
        } catch (IOException | NullPointerException e) {
            System.out.println("Error");
            e.printStackTrace();
        }

        System.exit(0);
    }

    public static void main(String[] args) throws IOException {

        Main main = new Main();
        CommandExecutor commandExecutor = main.commandExecutor;

        String name = System.getProperty("user.name");




        ConsoleMenu.startToInteractWithTheUser();

    }
}
