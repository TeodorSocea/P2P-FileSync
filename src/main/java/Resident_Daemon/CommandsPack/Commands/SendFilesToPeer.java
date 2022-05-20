package Resident_Daemon.CommandsPack.Commands;

import Networking.Core.NetworkingComponent;
import Resident_Daemon.CommandsPack.Command;
import Resident_Daemon.Core.Singleton;
import Resident_Daemon.Utils.BasicFileUtils;
import Resident_Daemon.Utils.GetTextFiles;

import java.io.IOException;
import java.nio.file.Path;

public class SendFilesToPeer implements Command {

    private int swarmID;
    private int peerID;
    private String path;

    public SendFilesToPeer(int swarmID, int peerID, String path) {
        this.swarmID = swarmID;
        this.peerID = peerID;
        this.path = path;
    }

    @Override
    public boolean execute() {
        Path folderPath = Singleton.getSingletonObject().getFolderToSyncPath();
        NetworkingComponent networkingComponent = Singleton.getSingletonObject().getNetworkingComponent();

        for(var entry : GetTextFiles.getTextFiles(folderPath).entrySet()){
            byte[] bytesToSend = BasicFileUtils.GetBytesToSend(String.valueOf(entry.getKey()));

            try {

                networkingComponent.sentDataToPeer(bytesToSend, swarmID, peerID);

            } catch (IOException e) {
                System.out.println("Eroare la trimitere fisier");
                e.printStackTrace();
                return false;
            }
        }


        return true;
    }
}
