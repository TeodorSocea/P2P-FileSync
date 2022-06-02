package Resident_Daemon.CommandsPack.Commands;

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

public class ReceiveFiles implements Command {

    private int swarmID;

    public ReceiveFiles(int swarmID) {
        this.swarmID = swarmID;
    }

    private List<FileP2P> getLocalFiles(){
        List<FileP2P> originalFiles = new ArrayList<>();
        Path swarmFolderPath = BasicFileUtils.GetSwarmFolderPath(swarmID);
        for(var entry : GetTextFiles.getTextFiles(swarmFolderPath).entrySet()){
            String fileName = entry.getKey().toString();
            String fileData = new String(entry.getValue(), StandardCharsets.UTF_8);
            FileP2P fileP2P = new FileP2P(fileName, fileData, 1);
            originalFiles.add(fileP2P);
        }

        return originalFiles;
    }


    @Override
    public boolean execute() {
        Version_Control_Component vcc = Singleton.getSingletonObject().getVersion_control_component();
        UserData userData = Singleton.getSingletonObject().getUserData();
        String versionFileData = BasicFileUtils.GetIfExistsVersionFileData(swarmID);
        if(versionFileData != null) {
            vcc.setFisierVersiuni(versionFileData);
        }


        vcc.setOriginalFiles(getLocalFiles());
        vcc.setOtherFiles(userData.getOtherFiles());

        try {

            vcc.compare();


            List<FileP2P> fileToWrite = vcc.getOriginalFiles();

            for(var fileData : fileToWrite){
                BasicFileUtils.WriteFileToFolder(swarmID, fileData.getFileName(), fileData.getData());
            }

            BasicFileUtils.WriteVersionFile(swarmID, vcc.getVersionFileData());

        } catch (IOException e) {
            System.out.println("Eroare la scriere fisier");
            e.printStackTrace();
        }


        return true;
    }
}
