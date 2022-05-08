package Resident_Daemon.Core;

import Resident_Daemon.CommandsPack.CommandExecutor;
import Resident_Daemon.MenuPack.ConsoleMenu;
import Resident_Daemon.PackageOS.DetectOS;

public class Main {
    private Singleton mainData;
    CommandExecutor commandExecutor;

    public Main() {
        commandExecutor = new CommandExecutor();

        mainData = Singleton.getSingletonObject();
        mainData.setOperatingSystem(new DetectOS().getOperatingSystem());

    }

    public static void main(String[] args) {
        Main main = new Main();
        CommandExecutor commandExecutor = main.commandExecutor;

        System.out.println("App started! System detected: " + main.mainData.getOperatingSystem());


        ConsoleMenu.startToInteractWithTheUser();



    }
}
