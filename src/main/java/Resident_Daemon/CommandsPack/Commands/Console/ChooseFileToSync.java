package Resident_Daemon.CommandsPack.Commands.Console;

import Networking.Core.NetworkingComponent;
import Resident_Daemon.BasicFileUtils;
import Resident_Daemon.CommandsPack.Commands.Command;
import Resident_Daemon.CommandsPack.Commands.ExceptionModule;
import Resident_Daemon.Core.Singleton;
import Resident_Daemon.Exceptions.NoFolderIsSelected;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.Scanner;

public class ChooseFileToSync extends ExceptionModule implements Command{

    private void IsFolderSelected() throws NoFolderIsSelected {
        String folderPath = Singleton.getSingletonObject().getFolderToSyncPath();

        if (folderPath == null){
            throw new NoFolderIsSelected("Choose the folder to sync first!");
        }

    }

    private String GetFilePath(Scanner input){
        String folderPath = Singleton.getSingletonObject().getFolderToSyncPath();

        System.out.println("Input file relative path from \"" + folderPath + "\": ");

        return input.nextLine();
    }

    private byte[] GetBytesToSend(String fileRelativePath) throws InvalidPathException{

        String folderPath = Singleton.getSingletonObject().getFolderToSyncPath();

        Path filePath = Paths.get(folderPath, fileRelativePath);

        if(!Files.isRegularFile(filePath)) throw new InvalidPathException(filePath.toString(), "Wrong path");

        byte[] out = BasicFileUtils.file2bytes(filePath);
        String fileContent = new String(out, StandardCharsets.UTF_8);
        StringBuilder stringBuilder = new StringBuilder(fileRelativePath + "!" + fileContent);

        out = stringBuilder.toString().getBytes(StandardCharsets.UTF_8);

        return out;
    }

    private int GetSwarmID(Scanner input){

        System.out.println("Input swarm's ID: ");

        String sID = input.nextLine();
        int swarmID = Integer.parseInt(sID);

        return swarmID;
    }

    @Override
    public boolean execute() {
        NetworkingComponent networkingComponent = Singleton.getSingletonObject().getNetworkingComponent();


        Scanner input = new Scanner(System.in);

        try {
            IsFolderSelected();
        } catch (NoFolderIsSelected e) {
            e.getMessage();
            setException(e);
            return false;
        }

        String fileRelativePath = GetFilePath(input);

        try {

            byte[] out = GetBytesToSend(fileRelativePath);
            int swarmID = GetSwarmID(input);

            networkingComponent.sendDataToPeers(out, swarmID);

        } catch (InvalidPathException e) {
            System.out.println("Invalid file path! " + e.getMessage());
            setException(e);
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            setException(e);
            return false;
        } catch (NumberFormatException e) {
            System.out.println("Invalid ID!");
            setException(e);
            return false;
        }

        System.out.println("File sent!");

        return true;
    }
}
