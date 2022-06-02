package Resident_Daemon.CommandsPack.Commands;

import Networking.Core.NetworkingComponent;
import Resident_Daemon.CommandsPack.Command;
import Resident_Daemon.Core.Singleton;
import Resident_Daemon.Core.SyncRecord;
import Resident_Daemon.Core.UserData;
import Resident_Daemon.Utils.BasicFileUtils;
import Resident_Daemon._UnitTests.ExceptionModule;
import Version_Control.Version_Control_Component;
import javafx.util.Pair;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class SyncSwarm extends ExceptionModule implements Command {

    private Integer swarmID;

    public SyncSwarm(Integer swarmID) {
        this.swarmID = swarmID;
    }

    private List<Pair<String,Long>> GetMasterPairList(List<SyncRecord> syncRecordList) {
        List<Pair<String,Long>> localRecords = new ArrayList<>();
        for(SyncRecord syncRecord : syncRecordList){
            Pair<String, Long> pair = new Pair<>(syncRecord.getFileRelPath(), syncRecord.getLastModifiedTimeStamp());
            localRecords.add(pair);
        }
        return localRecords;
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
                userData.setEnableToWriteAllFiles(false);

                BasicFileUtils.CreateLocalMasterFile(swarmID);

                Version_Control_Component vc = Singleton.getSingletonObject().getVersion_control_component();
                // here we compare with Version Control
                List<SyncRecord> localMasterFileRecords = userData.getLocalMasterFile();
                List<SyncRecord> otherMasterFileRecords = userData.getOtherMasterFile();

                List<Pair<String,Long>> localRecords = GetMasterPairList(localMasterFileRecords);
                List<Pair<String,Long>> otherRecords = GetMasterPairList(otherMasterFileRecords);

                vc.setLocalMasterFile(localRecords);
                vc.setOtherMasterFile(otherRecords);

                vc.compareMasterFile();

                if(localRecords.size() > 0) {
                    StringBuilder stringBuilder = new StringBuilder();

                    for(Pair pair : localRecords) {
                        stringBuilder.append(pair.getKey() + "!");
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

                    Singleton.getSingletonObject().getCommandExecutor().ExecuteOperation(new ReceiveFiles(swarmID));
                }


                System.out.println("Sync done!");

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }


        return true;
    }
}
