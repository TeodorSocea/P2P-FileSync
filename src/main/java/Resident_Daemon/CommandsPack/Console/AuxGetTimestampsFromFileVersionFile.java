package Resident_Daemon.CommandsPack.Console;

import Resident_Daemon.CommandsPack.AuxiliarCommand;
import Resident_Daemon.CommandsPack.Command;
import Resident_Daemon.CommandsPack.CommandExecutor;
import Resident_Daemon.CommandsPack.Commands.GetFilesFromVersionFile;
import Resident_Daemon.CommandsPack.Commands.GetTimestampsFromFileVersionFile;
import Resident_Daemon.Core.Input;
import Resident_Daemon.Core.Singleton;

public class AuxGetTimestampsFromFileVersionFile implements AuxiliarCommand {
    private String GetFileName(){
        System.out.println("Input File Name: ");
        String fileName = Input.nextLine();
        return fileName;
    }
    @Override
    public Command run() {
        CommandExecutor commandExecutor = Singleton.getSingletonObject().getCommandExecutor();
        commandExecutor.ExecuteOperation(new GetTimestampsFromFileVersionFile(GetFileName()));
        System.out.println(Singleton.getSingletonObject().getUserData().getTimestampVersionFileFiles());
        return null;
    }
}
