package Resident_Daemon.Core;

import Resident_Daemon.CommandsPack.CommandExecutor;
import Resident_Daemon.MenuPack.ConsoleMenu;
import java.io.IOException;

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

    public static void main(String[] args) {

//        testSerialization();

        Main main = new Main();
        CommandExecutor commandExecutor = main.commandExecutor;

        System.out.println("App started! System detected: " + main.mainData.getOperatingSystem());
        String name = System.getProperty("user.name");
        System.out.println("System name: " + name);


        SignalReceiver signalReceiver = new SignalReceiver();

        new Thread(signalReceiver).start();

        ConsoleMenu.startToInteractWithTheUser();



    }
}
