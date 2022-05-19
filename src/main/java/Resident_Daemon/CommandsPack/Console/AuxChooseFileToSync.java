package Resident_Daemon.CommandsPack.Console;

import Resident_Daemon.CommandsPack.AuxiliarCommand;
import Resident_Daemon.CommandsPack.Commands.ChooseFileToSync;
import Resident_Daemon.CommandsPack.Command;
import Resident_Daemon.Core.Singleton;
import Resident_Daemon.Exceptions.NoFolderIsSelected;
import Resident_Daemon.Core.Input;

import java.nio.file.*;

public class AuxChooseFileToSync implements AuxiliarCommand {

    private String GetFilePath(){
        String folderPath = Singleton.getSingletonObject().getFolderToSyncPath().toString();

        System.out.println("Input file relative path from \"" + folderPath + "\": ");

        return Input.nextLine();
    }

    private int GetSwarmID(){

        System.out.println("Input swarm's ID: ");

        String sID = Input.nextLine();
        int swarmID = Integer.parseInt(sID);

        return swarmID;
    }

    @Override
    public Command run() {
        String fileRelativePath;
        int swarmID;

        try {

            fileRelativePath = GetFilePath();
            swarmID = GetSwarmID();

        } catch (NumberFormatException e) {
            System.out.println("Invalid ID!");
            return null;
        }

        return new ChooseFileToSync(fileRelativePath, swarmID);
    }
}
