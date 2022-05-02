package Resident_Daemon.CommandsPack.Commands.Console;

import Resident_Daemon.BasicFileUtils;
import Resident_Daemon.CommandsPack.Commands.Command;
import Resident_Daemon.Singleton;

import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.Scanner;

public class ChooseFileToSync implements Command {

    @Override
    public boolean execute() {

        String folderPath = Singleton.getSingletonObject().getFolderToSyncPath();

        System.out.println("The folder path is: " + folderPath);
        if (folderPath == null){
            System.out.println("Choose the folder to sync first!");
            return false;
        }

        Scanner input = new Scanner(System.in);

        System.out.println("Input file relative path from " + folderPath + ": ");
        String fileRelativePath = input.nextLine();
        try {
            Path filePath = Paths.get(folderPath, fileRelativePath);

            if(!Files.isRegularFile(filePath)) return false;

            byte[] out = BasicFileUtils.file2bytes(filePath);
            String fileContent = new String(out, StandardCharsets.UTF_8);
            StringBuilder stringBuilder = new StringBuilder(fileRelativePath + "!" + fileContent);
            // relative filepath + rights

            System.out.println("File content:\n--------------------START--------------------\n" + fileContent);

            System.out.println("\n--------------------END--------------------\nFile sent to peers");

            return true;

        } catch (InvalidPathException e) {
            System.out.println("Invalid file path!");
            return false;
        }


    }
}
