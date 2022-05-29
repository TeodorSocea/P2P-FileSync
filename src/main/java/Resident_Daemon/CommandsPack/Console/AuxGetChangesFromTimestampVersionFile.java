package Resident_Daemon.CommandsPack.Console;

import Resident_Daemon.CommandsPack.AuxiliarCommand;
import Resident_Daemon.CommandsPack.Command;
import Resident_Daemon.CommandsPack.CommandExecutor;
import Resident_Daemon.CommandsPack.Commands.GetChangesFromTimestampVersionFile;
import Resident_Daemon.CommandsPack.Commands.GetTimestampsFromFileVersionFile;
import Resident_Daemon.Core.Input;
import Resident_Daemon.Core.Singleton;

public class AuxGetChangesFromTimestampVersionFile implements AuxiliarCommand {
    private String GetFileName(){
        System.out.println("Input File Name: ");
        String fileName = Input.nextLine();
        return fileName;
    }
    private String GetTimestamp(){
        System.out.println("Input Timestamp: ");
        String timestamp = Input.nextLine();
        return timestamp;
    }
    @Override
    public Command run() {
        CommandExecutor commandExecutor = Singleton.getSingletonObject().getCommandExecutor();
        commandExecutor.ExecuteOperation(new GetChangesFromTimestampVersionFile(GetFileName(), GetTimestamp()));
        System.out.println(Singleton.getSingletonObject().getUserData().getChangesFileVersionFile());
        return null;
    }
}
