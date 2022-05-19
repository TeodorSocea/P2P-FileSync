package Resident_Daemon.CommandsPack.Commands;

import Networking.Core.NetworkingComponent;
import Resident_Daemon.CommandsPack.Command;
import Resident_Daemon.Core.Input;
import Resident_Daemon.Core.Singleton;
import Resident_Daemon._UnitTests.ExceptionModule;

import java.io.IOException;

public class InviteToSwarm extends ExceptionModule implements Command {

    private String IP;
    private int swarmID;

    public InviteToSwarm(String IP, int swarmID) {
        this.IP = IP;
        this.swarmID = swarmID;
    }

    @Override
    public boolean execute() {
        NetworkingComponent networkingComponent = Singleton.getSingletonObject().getNetworkingComponent();

        try {
            networkingComponent.inviteIPToSwarm(IP, swarmID);
        } catch (IOException e) {
            System.out.println("Error at inviting!");
            setException(e);
            return false;
        }

        return true;
    }
}
