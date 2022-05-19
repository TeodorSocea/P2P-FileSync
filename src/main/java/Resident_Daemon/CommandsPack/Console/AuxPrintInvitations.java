package Resident_Daemon.CommandsPack.Console;

import Networking.Core.NetworkingComponent;
import Resident_Daemon.CommandsPack.AuxiliarCommand;
import Resident_Daemon.CommandsPack.Command;
import Resident_Daemon.CommandsPack.CommandExecutor;
import Resident_Daemon.CommandsPack.Commands.PrintInvitations;
import Resident_Daemon.Core.Singleton;

public class AuxPrintInvitations implements AuxiliarCommand {

    @Override
    public Command run() {
        CommandExecutor commandExecutor = Singleton.getSingletonObject().getCommandExecutor();
        commandExecutor.ExecuteOperation(new PrintInvitations());

        System.out.println(Singleton.getSingletonObject().getUserData().getUserInvitations());
        return null;
    }
}
