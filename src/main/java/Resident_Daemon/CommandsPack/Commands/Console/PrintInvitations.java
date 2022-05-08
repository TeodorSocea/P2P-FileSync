package Resident_Daemon.CommandsPack.Commands.Console;

import Networking.Core.NetworkingComponent;
import Resident_Daemon.CommandsPack.Commands.Command;
import Resident_Daemon.Core.Singleton;

public class PrintInvitations implements Command {

    @Override
    public boolean execute() {
        NetworkingComponent networkingComponent = Singleton.getSingletonObject().getNetworkingComponent();

        networkingComponent.printInvitations();

        return true;
    }
}
