package Resident_Daemon.CommandsPack.Commands.Console;

import Networking.Core.NetworkingComponent;
import Resident_Daemon.CommandsPack.Commands.Command;
import Resident_Daemon.Singleton;

import java.io.IOException;
import java.util.Scanner;

public class PrintIPs implements Command {

    @Override
    public boolean execute() {
        NetworkingComponent networkingComponent = Singleton.getSingletonObject().getNetworkingComponent();

        networkingComponent.printCommonIPPool();

        return true;
    }
}
