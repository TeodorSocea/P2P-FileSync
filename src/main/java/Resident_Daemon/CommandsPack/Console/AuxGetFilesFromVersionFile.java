package Resident_Daemon.CommandsPack.Console;

import Resident_Daemon.CommandsPack.AuxiliarCommand;
import Resident_Daemon.CommandsPack.Command;
import Resident_Daemon.CommandsPack.CommandExecutor;
import Resident_Daemon.CommandsPack.Commands.GetFilesFromVersionFile;
import Resident_Daemon.CommandsPack.Commands.PrintIPs;
import Resident_Daemon.Core.Singleton;
import Version_Control.Version_Control_Component;

public class AuxGetFilesFromVersionFile  implements AuxiliarCommand {
    @Override
    public Command run() {
        CommandExecutor commandExecutor = Singleton.getSingletonObject().getCommandExecutor();
        commandExecutor.ExecuteOperation(new GetFilesFromVersionFile());
        System.out.println(Singleton.getSingletonObject().getUserData().getVersionFileFiles());
        return null;
    }
}
