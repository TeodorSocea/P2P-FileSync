package Resident_Daemon.CommandsPack.Commands;

import Networking.Core.NetworkingComponent;
import Resident_Daemon.CommandsPack.Command;
import Resident_Daemon.Core.Singleton;
import Resident_Daemon.Core.UserData;
import Resident_Daemon.Utils.BasicFileUtils;
import Resident_Daemon.Utils.GetTextFiles;
import Version_Control.FileP2P;
import Version_Control.Version_Control_Component;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import static Resident_Daemon.Utils.GetTextFiles.getTextFiles;

public class ReceiveFiles implements Command {

    private List<FileP2P> getLocalFiles(){
        List<FileP2P> originalFiles = new ArrayList<>();
        Path folderPath = Singleton.getSingletonObject().getFolderToSyncPath();
        for(var entry : GetTextFiles.getTextFiles(folderPath).entrySet()){
            String fileName = entry.getKey().toString();
            String fileData = new String(entry.getValue(), StandardCharsets.UTF_8);
            FileP2P fileP2P = new FileP2P(fileName, fileData, 1);
            originalFiles.add(fileP2P);
        }

        return originalFiles;
    }

    private void ScriuCeVreau(String versionFileData){

        Path folderPath = Singleton.getSingletonObject().getFolderToSyncPath();

        Path filePath = Paths.get(String.valueOf(folderPath), Singleton.getSingletonObject().VERSION_FILE_DATA_NAME);

        try {
            Files.writeString(filePath, versionFileData);
        } catch (IOException e) {
            System.out.println("Error at writing Version File!");
        }
    }

    private void getIfExistsVersionFileData(Version_Control_Component vcc){
        Path folderPath = Singleton.getSingletonObject().getFolderToSyncPath();
        Path filePath = Paths.get(String.valueOf(folderPath), Singleton.getSingletonObject().VERSION_FILE_DATA_NAME);

        Path versionFile = filePath;

        byte[] versionFileBytes = BasicFileUtils.file2bytes(versionFile);

        if(versionFileBytes != null){
            String versionFileData = new String(versionFileBytes, StandardCharsets.UTF_8);
            vcc.setFisierVersiuni(versionFileData);
        }

    }

    @Override
    public boolean execute() {
        Version_Control_Component vcc = Singleton.getSingletonObject().getVersion();
        UserData userData = Singleton.getSingletonObject().getUserData();
        getIfExistsVersionFileData(vcc);

//        vcc.setVersionFileData(vcc.getVersionFileData()); trebuie sa il scriu aici

        vcc.setOriginalFiles(getLocalFiles());
        vcc.setOtherFiles(userData.getOtherFiles());

        try {

            vcc.compare();

            ScriuCeVreau(vcc.getVersionFileData());

            List<FileP2P> fileToWrite = vcc.getOriginalFiles();

            for(var fileData : fileToWrite){
                BasicFileUtils.WriteFileToFolder(fileData.getFileName(), fileData.getData());
            }

        } catch (IOException e) {
            System.out.println("Eroare la scriere fisier");
            e.printStackTrace();
        }

        userData.setEnableToWriteAllFiles(false);

        return true;
    }
}
