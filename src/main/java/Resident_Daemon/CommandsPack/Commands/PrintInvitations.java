package Resident_Daemon.CommandsPack.Commands;

import Networking.Core.NetworkingComponent;
import Resident_Daemon.CommandsPack.Command;
import Resident_Daemon.Core.Singleton;
import Resident_Daemon._UnitTests.ExceptionModule;

public class PrintInvitations extends ExceptionModule implements Command {

    @Override
    public boolean execute() {
        NetworkingComponent networkingComponent = Singleton.getSingletonObject().getNetworkingComponent();

        networkingComponent.printInvitations();

        return true;
    }
}
