package Resident_Daemon.CommandsPack.Commands;

import Networking.Core.NetworkingComponent;
import Resident_Daemon.CommandsPack.Command;
import Resident_Daemon.Core.Singleton;
import Resident_Daemon._UnitTests.ExceptionModule;

public class CreateSwarm extends ExceptionModule implements Command {

    @Override
    public boolean execute() {
        NetworkingComponent networkingComponent = Singleton.getSingletonObject().getNetworkingComponent();

        networkingComponent.createNewSwarm();
//        NetworkingComponent nc = new NetworkingComponent(33531);
//        nc.start();
//
//        nc.joinSwarm(18);
//        nc.getSwarmManager().getByID(18).setSelfID(69);
//
//        ConsoleMenu.pageNumber += 1 % 2;

        return true;
    }
}