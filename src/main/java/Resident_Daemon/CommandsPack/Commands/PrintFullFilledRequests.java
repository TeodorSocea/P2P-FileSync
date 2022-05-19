package Resident_Daemon.CommandsPack.Commands;

import Resident_Daemon.CommandsPack.Command;
import Resident_Daemon.Core.Singleton;

public class PrintFullFilledRequests implements Command {
    @Override
    public boolean execute() {

        for(var pair : Singleton.getSingletonObject().getNetworkingComponent().getFulfilledRequests()) {
            System.out.println("key: " + pair.getKey());
            System.out.println("value: " + pair.getValue());
        }

        return true;
    }
}
