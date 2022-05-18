package Resident_Daemon.Core;

import Resident_Daemon.CommandsPack.CommandExecutor;
import Resident_Daemon.MenuPack.ConsoleMenu;
import Resident_Daemon.PackageOS.DetectOS;

import java.io.FileNotFoundException;
import java.io.IOException;

public class Main {
    private Singleton mainData;
    CommandExecutor commandExecutor;

    public Main() {
        commandExecutor = new CommandExecutor();

        mainData = Singleton.getSingletonObject();
        mainData.setOperatingSystem(new DetectOS().getOperatingSystem());

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


        ConsoleMenu.startToInteractWithTheUser();



    }
}
