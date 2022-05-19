package Resident_Daemon.CommandsPack.Commands;

import Networking.Core.NetworkingComponent;
import Resident_Daemon.CommandsPack.Command;
import Resident_Daemon.Core.Singleton;
import Resident_Daemon.Exceptions.NoFolderIsSelected;
import Resident_Daemon.Utils.BasicFileUtils;
import Resident_Daemon._UnitTests.ExceptionModule;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ChooseFileToSync extends ExceptionModule implements Command {

    private String fileRelativePath;
    private Integer swarmID;

    public ChooseFileToSync(String fileRelativePath, Integer swarmID) {
        this.fileRelativePath = fileRelativePath;
        this.swarmID = swarmID;
    }


    private void IsFolderSelected() throws NoFolderIsSelected {
        String folderPath = Singleton.getSingletonObject().getFolderToSyncPath().toString();

        if (folderPath == null){
            throw new NoFolderIsSelected("Choose the folder to sync first!");
        }

    }


    private byte[] GetBytesToSend(String fileRelativePath) throws InvalidPathException{

        String folderPath = Singleton.getSingletonObject().getFolderToSyncPath().toString();

        Path filePath = Paths.get(folderPath, fileRelativePath);

        if(!Files.isRegularFile(filePath)) throw new InvalidPathException(filePath.toString(), "Wrong path");

        byte[] out = BasicFileUtils.file2bytes(filePath);
        String fileContent = new String(out, StandardCharsets.UTF_8);
        StringBuilder stringBuilder = new StringBuilder(fileRelativePath + "!" + fileContent);

        out = stringBuilder.toString().getBytes(StandardCharsets.UTF_8);

        return out;
    }

    @Override
    public boolean execute() {
        NetworkingComponent networkingComponent = Singleton.getSingletonObject().getNetworkingComponent();

        try {
            IsFolderSelected();

            byte[] out = GetBytesToSend(fileRelativePath);

            networkingComponent.sendDataToPeers(out, swarmID);

        } catch (NoFolderIsSelected | InvalidPathException | IOException e) {
            if(e instanceof NoFolderIsSelected){
                System.out.println(e.getMessage());
            } else if(e instanceof IOException) {
                e.printStackTrace();
            } else if(e instanceof InvalidPathException){
                System.out.println("Invalid file path! " + e.getMessage());
            }
            setException(e);
            return false;
        }

        System.out.println("File sent!");

        return true;
    }
}
