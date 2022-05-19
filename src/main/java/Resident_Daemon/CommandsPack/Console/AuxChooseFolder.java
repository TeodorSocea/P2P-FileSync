package Resident_Daemon.CommandsPack.Console;

import Resident_Daemon.CommandsPack.AuxiliarCommand;
import Resident_Daemon.CommandsPack.Command;
import Resident_Daemon.CommandsPack.Commands.ChooseFolder;
import Resident_Daemon.Core.Input;

import java.nio.file.InvalidPathException;

public class AuxChooseFolder implements AuxiliarCommand {

    private String GetFolderPath() throws InvalidPathException {
        System.out.println("Input folder path: ");

        String folderPath = Input.nextLine();


        return folderPath;
    }

    @Override
    public Command run() {

        String folderPath = GetFolderPath();

        return new ChooseFolder(folderPath);

    }
}