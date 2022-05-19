package Resident_Daemon.CommandsPack.Commands;

import Resident_Daemon.CommandsPack.Command;
import Resident_Daemon.CommandsPack.ConsolePageSwitch;
import Resident_Daemon.Core.Input;
import Resident_Daemon.Core.Singleton;
import Resident_Daemon.MenuPack.ConsoleMenu;
import Resident_Daemon.Utils.BasicFileUtils;
import Resident_Daemon._UnitTests.ExceptionModule;

import java.io.FileNotFoundException;
import java.nio.file.InvalidPathException;

public class ChooseFolder extends ExceptionModule implements Command, ConsolePageSwitch {

    String folderPath;

    public ChooseFolder(String folderPath) {
        this.folderPath = folderPath;
    }



    @Override
    public boolean execute() {


        try {
            Singleton.getSingletonObject().setFolderToSyncPath(folderPath);
        } catch (InvalidPathException e) {
            System.out.println("Invalid path!");

            setException(e);

            return false;
        }

        String path = Singleton.getSingletonObject().getFolderToSyncPath().toString();
        System.out.println("The folder path is: " + path);

        return true;
    }

    @Override
    public void ChangePage() {
        ConsoleMenu.pageNumber += 1 % 2;
    }
}


/*
package Resident_Daemon.CommandsPack.Console;

import Resident_Daemon.FileAux.BasicFileUtils;
import Resident_Daemon.CommandsPack.Command;
import Resident_Daemon.Core.Singleton;

import java.util.Scanner;

public class ChooseFolder implements Command {

    @Override
    public boolean execute() {

        Scanner input = new Scanner(System.in);

        System.out.println("Input folder path: ");
        String folderPath = input.nextLine();

        if(BasicFileUtils.isDirectory(folderPath)){
            Singleton.getSingletonObject().setFolderToSyncPath(folderPath);
            String path = Singleton.getSingletonObject().getFolderToSyncPath();
            System.out.println("The folder path is: " + path);
            return true;
        }

        System.out.println("The path is not a directory!");

        return false;
    }
}
*/