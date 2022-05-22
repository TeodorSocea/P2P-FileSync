package Resident_Daemon.CommandsPack.Commands;

import Networking.Core.NetworkingComponent;
import Resident_Daemon.CommandsPack.Command;
import Resident_Daemon.Core.Singleton;
import Resident_Daemon.Core.UserData;
import Resident_Daemon.Utils.BasicFileUtils;
import Resident_Daemon.Utils.GetTextFiles;
import Version_Control.FileP2P;
import Version_Control.Version_Control_Component;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
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

    @Override
    public boolean execute() {
        Version_Control_Component vcc = Singleton.getSingletonObject().getVersion();
        UserData userData = Singleton.getSingletonObject().getUserData();

//        vcc.setVersionFileData(vcc.getVersionFileData()); trebuie sa il scriu aici


        vcc.setOriginalFiles(getLocalFiles());
        vcc.setOtherFiles(userData.getOtherFiles());

        try {

            vcc.compare();
            List<FileP2P> fileToWrite = vcc.getOriginalFiles();

            for(var fileData : fileToWrite){
                BasicFileUtils.WriteFileToFolder(fileData.getFileName(), fileData.getData());
            }

        } catch (IOException e) {
            System.out.println("Eroare la scriere fisier");
            e.printStackTrace();
        }

        return true;
    }
}
