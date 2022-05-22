package Resident_Daemon.CommandsPack.Commands;

import Networking.Core.NetworkingComponent;
import Resident_Daemon.CommandsPack.Command;
import Resident_Daemon.Core.Singleton;

import java.net.UnknownHostException;

public class PrintSelfIP implements Command {
    @Override
    public boolean execute() {
        NetworkingComponent networkingComponent = Singleton.getSingletonObject().getNetworkingComponent();

        try {
            Singleton.getSingletonObject().getUserData().setSelfIP(networkingComponent.getSelfIp());
        } catch (UnknownHostException e) {
            System.out.println("Error at PrintSelfIP - command :(");
        }

        return true;
    }
}
