package Resident_Daemon.CommandsPack.Commands;

import Networking.Core.NetworkingComponent;
import Resident_Daemon.CommandsPack.Command;
import Resident_Daemon.Core.Singleton;
import Resident_Daemon.Utils.BasicFileUtils;
import Resident_Daemon.Utils.GetTextFiles;

import java.io.IOException;
import java.nio.file.Path;

public class ReceiveFiles implements Command {

    private int swarmID;
    private int peerID;

    public ReceiveFiles(int swarmID, int peerID) {
        this.swarmID = swarmID;
        this.peerID = peerID;
    }

    @Override
    public boolean execute() {
        NetworkingComponent networkingComponent = Singleton.getSingletonObject().getNetworkingComponent();

        byte[] dataReceived = networkingComponent.getDataFromDataPipeline(swarmID, peerID);

        try {
            BasicFileUtils.WriteFileToFolder(dataReceived);
        } catch (IOException e) {
            System.out.println("Eroare la scriere fisier");
            e.printStackTrace();
        }

        return true;
    }
}
