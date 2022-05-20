package Resident_Daemon.CommandsPack.Commands;

import Networking.Core.NetworkingComponent;
import Resident_Daemon.CommandsPack.Command;
import Resident_Daemon.Core.Input;
import Resident_Daemon.Core.Singleton;
import Resident_Daemon._UnitTests.ExceptionModule;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class RespondToInvitation extends ExceptionModule implements Command {

    private Integer invitationIndex;
    private boolean invitationResponse;

    public RespondToInvitation(Integer invitationIndex, boolean invitationResponse) {
        this.invitationIndex = invitationIndex;
        this.invitationResponse = invitationResponse;
    }

    private String getFilePath(String data){
        return data.substring(0, data.indexOf("!"));
    }

    private String getContent(String data){
        return data.substring(data.indexOf("!") + 1);
    }

//    private void getSyncedFolder(NetworkingComponent networkingComponent){
//
//        Input.confScanner();
//        System.out.println("Input the swarmID: ");
//        String swarmID = Input.nextLine();
//        System.out.println("Input the peerID: ");
//        String peerID = Input.nextLine();
//
//        try {
//            int sID = Integer.parseInt(swarmID);
//            int pID = Integer.parseInt(peerID);
//
//
//            List<byte[]> dataList = null;
//            while(dataList == null){
//                dataList = networkingComponent.getDataFromDataPipeline(sID, pID);
//            }
//            for(var data : dataList){
//                String str_data = new String(data);
//                String fileRelPath = getFilePath(str_data);
//                Path dir = Singleton.getSingletonObject().getFolderToSyncPath();
//                Path fileFullPath = Paths.get(dir.toString(),fileRelPath);
//                String fileContent = getContent(str_data);
//
//
//                try {
//                    Files.writeString(fileFullPath, fileContent);
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//
//            }
//
//        } catch (NumberFormatException e){
//            System.out.println("Invalid number");
//        }
//
//
//    }

    @Override
    public boolean execute() {
        NetworkingComponent networkingComponent = Singleton.getSingletonObject().getNetworkingComponent();


        try {

            networkingComponent.respondToInvitationToSwarm(invitationIndex, invitationResponse);
        } catch (IOException e) {
            System.out.println("Error at responding!");
            setException(e);
            return false;
        }

        return true;
    }
}