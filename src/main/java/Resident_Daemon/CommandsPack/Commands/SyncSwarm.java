package Resident_Daemon.CommandsPack.Commands;

import Networking.Core.NetworkingComponent;
import Resident_Daemon.CommandsPack.Command;
import Resident_Daemon.Core.Singleton;
import Resident_Daemon.Core.SyncRecord;
import Resident_Daemon.Core.UserData;
import Resident_Daemon.Exceptions.NoFolderIsSelected;
import Resident_Daemon.Utils.BasicFileUtils;
import Resident_Daemon.Utils.GetTextFiles;
import Resident_Daemon._UnitTests.ExceptionModule;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class SyncSwarm extends ExceptionModule implements Command {

    private Integer swarmID;

    public SyncSwarm(Integer swarmID) {
        this.swarmID = swarmID;
    }

    private void CreateLocalMasterFile() {
        UserData userData = Singleton.getSingletonObject().getUserData();
        Path folderPath = Singleton.getSingletonObject().getFolderToSyncPath();

        for(var file : GetTextFiles.getTextFiles(folderPath).entrySet()){
            userData.getLocalMasterFile().add(new SyncRecord (file.getKey().toString(), true));
        }
    }

    @Override
    public boolean execute() {
        NetworkingComponent networkingComponent = Singleton.getSingletonObject().getNetworkingComponent();
        UserData userData = Singleton.getSingletonObject().getUserData();

        var peers = networkingComponent.getSwarms().get(swarmID).getPeers();
        if(peers.isEmpty()){
            System.out.println("No peers to sync with!");
            return false;
        }

        for(Integer peerID : peers.keySet()){
            userData.resetMasterFiles();
            userData.resetFileLists();
            userData.setEnableToWriteAllFiles(false);

            // do localMF
            // TODO
            // ...

            try {
                networkingComponent.requestDataFromSwarm(swarmID, peerID, BasicFileUtils.filePathMasterSyncFile);

                while (!userData.isReceivedMasterFile()) {
                    try {
                        TimeUnit.MILLISECONDS.sleep(50);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }

                userData.setReceivedMasterFile(false);

                CreateLocalMasterFile();

                // here we compare with Version Control
                List<SyncRecord> localMasterFileRecords = userData.getLocalMasterFile();
                List<SyncRecord> otherMasterFileRecords = userData.getOtherMasterFile();

                if(otherMasterFileRecords.size() > 0) {
                    StringBuilder stringBuilder = new StringBuilder();

                    for(SyncRecord syncRecord : otherMasterFileRecords) {
                        stringBuilder.append(syncRecord.getFileRelPath() + "!");
                    }
                    stringBuilder.deleteCharAt(stringBuilder.length() - 1);

                    networkingComponent.requestDataFromSwarm(swarmID, peerID, stringBuilder.toString());

                    while (!userData.isEnableToWriteAllFiles()) {
                        try {
                            TimeUnit.MILLISECONDS.sleep(50);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }

                    Singleton.getSingletonObject().getCommandExecutor().ExecuteOperation(new ReceiveFiles());
                }


                System.out.println("Sync done!");

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }


        return true;
    }
}
