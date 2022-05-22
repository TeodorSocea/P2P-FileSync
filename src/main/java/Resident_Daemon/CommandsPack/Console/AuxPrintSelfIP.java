package Resident_Daemon.CommandsPack.Console;

import Resident_Daemon.CommandsPack.AuxiliarCommand;
import Resident_Daemon.CommandsPack.Command;
import Resident_Daemon.CommandsPack.CommandExecutor;
import Resident_Daemon.CommandsPack.Commands.PrintSelfIP;
import Resident_Daemon.Core.Singleton;

public class AuxPrintSelfIP implements AuxiliarCommand {

    @Override
    public Command run() {
        CommandExecutor commandExecutor = Singleton.getSingletonObject().getCommandExecutor();
        commandExecutor.ExecuteOperation(new PrintSelfIP());

        System.out.println(Singleton.getSingletonObject().getUserData().getNearbyIPs());
        return null;
    }
}
