package Resident_Daemon.Core;

import Resident_Daemon.CommandsPack.CommandExecutor;
import Resident_Daemon.CommandsPack.Commands.Command;
import Resident_Daemon.PackageOS.DetectOS;
import Resident_Daemon.PackageOS.SpecificAPI;

public class Main {
    public static void main(String[] args) {
        System.out.println("App started!");
        String os = new DetectOS().getOperatingSystem();
        new SpecificAPI().runOSActions(); //detecteaza automat sistemul si face chestiile necesare (initial nu se cunoaste OS-ul)
        //new SpecificAPI().WindowsCommands(); executa comenzi specifice windows (initial se cunoaste OS-ul)
        //new SpecificAPI().UnixCommands(); executa comenzi specifice unix (initial se cunoaste OS-ul)
        //new SpecificAPI().MacOSCommands(); executa comenzi specifice macos (initial se cunoaste OS-ul)
        CommandExecutor commandExecutor = new CommandExecutor();
        commandExecutor.ExecuteOperation(new Command() {
            @Override
            public boolean execute() {
                System.out.println("Executed dummy command!");
                return false;
            }
        });

    }
}
