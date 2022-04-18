package Resident_Daemon.CommandsPack.Core;

import Resident_Daemon.CommandsPack.CommandExecutor;
import Resident_Daemon.CommandsPack.Commands.LocalAPI.NewFile;
import Resident_Daemon.PackageOS.DetectOS;

public class Main {
    public static void main(String[] args) {
        System.out.println("App started!");
        new DetectOS().getOperatingSystem();
        CommandExecutor commandExecutor = new CommandExecutor();
        commandExecutor.ExecuteOperation(new NewFile()); //detecteaza automat sistemul de operare si creaza un moruz.txt


    }
}
