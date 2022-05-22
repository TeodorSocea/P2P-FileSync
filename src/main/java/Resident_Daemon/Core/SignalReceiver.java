package Resident_Daemon.Core;

import Networking.Core.NetworkingComponent;
import Resident_Daemon.CommandsPack.CommandExecutor;
import Resident_Daemon.CommandsPack.Commands.ProcessRecievedFile;
import Resident_Daemon.CommandsPack.Commands.ReceiveFiles;
import Resident_Daemon.CommandsPack.Commands.SendFilesToPeer;

import java.util.concurrent.TimeUnit;

public class SignalReceiver implements Runnable {
    UserData userData = Singleton.getSingletonObject().getUserData();
    CommandExecutor commandExecutor = Singleton.getSingletonObject().getCommandExecutor();


    @Override
    public void run() {
        //seconds
        long notificationTime = 1;

        while(true){
            if(userData.isConnected()){
                NetworkingComponent networkingComponent = Singleton.getSingletonObject().getNetworkingComponent();
                // Daca are request sa trimita
                for(var triple : networkingComponent.getRequests()){
                    int swarmID = triple.getLeft();
                    int peerID = triple.getMiddle();
                    String path = triple.getRight();

                    commandExecutor.ExecuteOperation(new SendFilesToPeer(swarmID, peerID, path));
                }
                // Daca a primit
                for(var pair : networkingComponent.getFulfilledRequests()){
                    int swarmID = pair.getKey();
                    int peerID = pair.getValue();

                    commandExecutor.ExecuteOperation(new ProcessRecievedFile(swarmID, peerID));
                }

                commandExecutor.ExecuteOperation(new ReceiveFiles());
            }
            try {
                TimeUnit.SECONDS.sleep(notificationTime);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }
}
