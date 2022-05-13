package Resident_Daemon.CommandsPack.Commands.Console;

import Resident_Daemon.BasicFileUtils;
import Resident_Daemon.CommandsPack.Commands.Command;
import Resident_Daemon.CommandsPack.Commands.ExceptionModule;
import Resident_Daemon.Core.Singleton;
import Resident_Daemon.Input;
import Resident_Daemon.MenuPack.ConsoleMenu;

import java.nio.file.InvalidPathException;
import java.util.Scanner;

public class ChooseFolder extends ExceptionModule implements Command {

    private String GetFolderPath() throws InvalidPathException{
        System.out.println("Input folder path: ");

        String folderPath = Input.nextLine();

        if (!BasicFileUtils.isDirectory(folderPath)) {
            throw new InvalidPathException(folderPath, "Wrong path");
        }

        return folderPath;
    }

    @Override
    public boolean execute() {

        Input.confScanner();

        String folderPath;

        try {
            folderPath = GetFolderPath();
        } catch (InvalidPathException e) {
            System.out.println("The path is not a directory!");

            setException(e);

            return false;
        }

        Singleton.getSingletonObject().setFolderToSyncPath(folderPath);
        String path = Singleton.getSingletonObject().getFolderToSyncPath();
        System.out.println("The folder path is: " + path);

        ConsoleMenu.pageNumber += 1 % 2;
        return true;
    }
}


/*
package Resident_Daemon.CommandsPack.Commands.Console;

import Resident_Daemon.BasicFileUtils;
import Resident_Daemon.CommandsPack.Commands.Command;
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