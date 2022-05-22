package Resident_Daemon.CommandsPack.Commands;

import Networking.Core.NetworkingComponent;
import Resident_Daemon.CommandsPack.Command;
import Resident_Daemon.Core.Singleton;
import Resident_Daemon.Core.UserData;
import Resident_Daemon.Utils.BasicFileUtils;
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

        Pair<String, String> fileData = BasicFileUtils.GetFileData(dataReceived);

        FileP2P fileP2P = new FileP2P(fileData.getKey(), fileData.getValue(), 1000);

        userData.addToOtherFilesList(fileP2P);

        return true;
    }
}
