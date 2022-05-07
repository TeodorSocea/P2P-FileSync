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
