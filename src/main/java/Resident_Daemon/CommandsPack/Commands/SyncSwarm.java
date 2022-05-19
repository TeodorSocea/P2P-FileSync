package Resident_Daemon.CommandsPack.Commands;

import Networking.Core.NetworkingComponent;
import Resident_Daemon.CommandsPack.Command;
import Resident_Daemon.Core.Singleton;
import Resident_Daemon.Exceptions.NoFolderIsSelected;
import Resident_Daemon.Utils.BasicFileUtils;
import Resident_Daemon._UnitTests.ExceptionModule;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class SyncSwarm extends ExceptionModule implements Command {

    private Integer swarmID;
    private Integer peerID;

    public SyncSwarm(Integer swarmID, Integer peerID) {
        this.swarmID = swarmID;
        this.peerID = peerID;
    }

    @Override
    public boolean execute() {
        NetworkingComponent networkingComponent = Singleton.getSingletonObject().getNetworkingComponent();

        try {

            networkingComponent.requestDataFromSwarm(swarmID, peerID, ".");

        } catch (Exception e){
            e.printStackTrace();
        }



        return true;
    }
}
