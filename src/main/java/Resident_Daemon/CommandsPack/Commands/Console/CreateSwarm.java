package Resident_Daemon.CommandsPack.Commands.Console;

import Networking.Core.NetworkingComponent;
import Resident_Daemon.CommandsPack.Commands.Command;
import Resident_Daemon.ConsoleMenu;

public class CreateSwarm implements Command {

    @Override
    public boolean execute() {
        NetworkingComponent nc = new NetworkingComponent(33531);
        nc.start();

        nc.joinSwarm(18);
        nc.getSwarmManager().getByID(18).setSelfID(69);

        ConsoleMenu.pageNumber += 1 % 2;

        return false;
    }
}
