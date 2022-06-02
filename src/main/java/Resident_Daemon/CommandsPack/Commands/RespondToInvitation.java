package Resident_Daemon.CommandsPack.Commands;

import Networking.Core.NetworkingComponent;
import Resident_Daemon.CommandsPack.Command;
import Resident_Daemon.Core.Input;
import Resident_Daemon.Core.Singleton;
import Resident_Daemon.Utils.BasicFileUtils;
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

    @Override
    public boolean execute() {
        NetworkingComponent networkingComponent = Singleton.getSingletonObject().getNetworkingComponent();


        try {

            networkingComponent.respondToInvitationToSwarm(invitationIndex, invitationResponse);

            String swarmName = networkingComponent.getInvitations().get(invitationIndex).getSwarmName();
            BasicFileUtils.CreateSwarmFolder(swarmName);
            if(invitationResponse == true){
                Singleton.getSingletonObject().getUserData().setConnected(true, networkingComponent.getSwarms());
            }
        } catch (IOException e) {
            System.out.println("Error at responding!");
            setException(e);
            return false;
        }

        return true;
    }
}
