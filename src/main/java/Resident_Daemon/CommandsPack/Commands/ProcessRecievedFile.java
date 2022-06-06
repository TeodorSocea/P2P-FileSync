package Resident_Daemon.CommandsPack.Commands;

import Networking.Core.NetworkingComponent;
import Resident_Daemon.CommandsPack.Command;
import Resident_Daemon.Core.Singleton;
import Resident_Daemon.Core.SyncRecord;
import Resident_Daemon.Core.UserData;
import Resident_Daemon.Utils.BasicFileUtils;
import Resident_Daemon.Utils.FileData;
import Resident_Daemon.Utils.GetTextFiles;
import Version_Control.FileP2P;
import javafx.util.Pair;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class ProcessRecievedFile implements Command {

    private int swarmID;
    private int peerID;

    public ProcessRecievedFile(int swarmID, int peerID) {
        this.swarmID = swarmID;
        this.peerID = peerID;
    }

    @Override
    public boolean execute() {
        NetworkingComponent networkingComponent = Singleton.getSingletonObject().getNetworkingComponent();
        UserData userData = Singleton.getSingletonObject().getUserData();

        byte[] dataReceived = networkingComponent.getDataFromDataPipeline(swarmID, peerID);

        FileData fileData = BasicFileUtils.GetFileData(dataReceived);
//        System.out.println(fileData.getFileContent());

        if(fileData.getFileRelPath().contains(BasicFileUtils.filePathMasterSyncFile)) {
            List<SyncRecord> syncRecordList = BasicFileUtils.readRecordsFromString(fileData.getFileContent());
            for(SyncRecord syncRecord : syncRecordList){
                userData.getOtherMasterFile().add(syncRecord);
            }

            userData.setReceivedMasterFile(true);
        } else {

            FileP2P fileP2P = new FileP2P(fileData.getFileRelPath(), fileData.getFileContent(), fileData.getTimeStamp());

            userData.addToOtherFilesList(fileP2P);
        }




        return true;
    }
}
