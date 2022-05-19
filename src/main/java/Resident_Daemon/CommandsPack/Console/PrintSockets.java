package Resident_Daemon.CommandsPack.Console;

import Networking.Core.NetworkingComponent;
import Resident_Daemon.CommandsPack.Command;
import Resident_Daemon._UnitTests.ExceptionModule;
import Resident_Daemon.Core.Singleton;

public class PrintSockets extends ExceptionModule implements Command {

    @Override
    public boolean execute() {
        NetworkingComponent networkingComponent = Singleton.getSingletonObject().getNetworkingComponent();

        networkingComponent.printCommonSocketPool();

        return true;
    }
}
