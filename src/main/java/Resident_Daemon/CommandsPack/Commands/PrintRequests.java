package Resident_Daemon.CommandsPack.Commands;

import Resident_Daemon.CommandsPack.Command;
import Resident_Daemon.Core.Singleton;

public class PrintRequests implements Command {
    @Override
    public boolean execute() {


        for(var triple : Singleton.getSingletonObject().getNetworkingComponent().getRequests()) {
            System.out.println("1: " + triple.getLeft());
            System.out.println("2: " + triple.getMiddle());
            System.out.println("3: " + triple.getRight());
        }

        return true;
    }
}
