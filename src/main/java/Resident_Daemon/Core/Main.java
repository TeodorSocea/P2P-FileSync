package Resident_Daemon.Core;

import Resident_Daemon.CommandsPack.CommandExecutor;
import Resident_Daemon.CommandsPack.Commands.Command;

public class Main {
    public static void main(String[] args) {
        System.out.println("App started!");
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
