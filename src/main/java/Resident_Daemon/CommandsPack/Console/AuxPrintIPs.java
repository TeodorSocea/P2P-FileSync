package Resident_Daemon.CommandsPack.Console;

import Resident_Daemon.CommandsPack.AuxiliarCommand;
import Resident_Daemon.CommandsPack.Command;
import Resident_Daemon.CommandsPack.CommandExecutor;
import Resident_Daemon.CommandsPack.Commands.PrintIPs;
import Resident_Daemon._UnitTests.ExceptionModule;
import Resident_Daemon.Core.Singleton;

public class AuxPrintIPs implements AuxiliarCommand {

    @Override
    public Command run() {
        CommandExecutor commandExecutor = Singleton.getSingletonObject().getCommandExecutor();
        commandExecutor.ExecuteOperation(new PrintIPs());

        System.out.println(Singleton.getSingletonObject().getUserData().getNearbyIPs());
        return null;
    }
}
