package Resident_Daemon.CommandsPack.Commands;

import Networking.Core.NetworkingComponent;
import Resident_Daemon.CommandsPack.Command;
import Resident_Daemon.Core.Singleton;
import Resident_Daemon.Utils.BasicFileUtils;
import Resident_Daemon.Utils.GetTextFiles;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.Base64;
import java.util.Iterator;
import java.util.StringTokenizer;

public class SendFilesToPeer implements Command {

    private int swarmID;
    private int peerID;
    private String path;

    public SendFilesToPeer(int swarmID, int peerID, String path) {
        this.swarmID = swarmID;
        this.peerID = peerID;
        this.path = path;
    }

    private void SendData(byte[] bytesToSend){

        NetworkingComponent networkingComponent = Singleton.getSingletonObject().getNetworkingComponent();
        try {

            networkingComponent.sentDataToPeer(bytesToSend, swarmID, peerID);

        } catch (IOException e) {
            System.out.println("Eroare la trimitere fisier");
            e.printStackTrace();
        }
    }

    private final String ALL_FILES = ".";
    private final String MASTER_FILE = BasicFileUtils.filePathMasterSyncFile;


    @Override
    public boolean execute() {
        Path folderPath = Singleton.getSingletonObject().getFolderToSyncPath();


        if(path.equals(ALL_FILES)){

            for(var entry : GetTextFiles.getTextFiles(folderPath).entrySet()){
                byte[] bytesToSend = BasicFileUtils.GetBytesToSend(String.valueOf(entry.getKey()));

                SendData(bytesToSend);

            }
        } else if (path.contains(MASTER_FILE)) {
            try {
                BasicFileUtils.SaveRecordsToMasterFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
            byte[] bytesToSend = BasicFileUtils.GetBytesToSend(MASTER_FILE);

            BasicFileUtils.DeleteMaterFile();

            SendData(bytesToSend);

        } else {
            StringTokenizer st = new StringTokenizer(path, "!");

            for (Iterator<Object> it = st.asIterator(); it.hasNext(); ) {
                String fileRelPath = (String) it.next();

                byte[] bytesToSend = BasicFileUtils.GetBytesToSend(fileRelPath);

                SendData(bytesToSend);
            }
        }


        return true;
    }
}
