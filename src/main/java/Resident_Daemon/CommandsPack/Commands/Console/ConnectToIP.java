package Resident_Daemon.CommandsPack.Commands.Console;

import Networking.Core.NetworkingComponent;
import Resident_Daemon.CommandsPack.Commands.Command;
import Resident_Daemon.Core.Singleton;

import java.io.IOException;
import java.util.Scanner;

public class ConnectToIP implements Command {

    @Override
    public boolean execute() {
        NetworkingComponent networkingComponent = Singleton.getSingletonObject().getNetworkingComponent();

        Scanner input = new Scanner(System.in);

        System.out.println("Input IP: ");
        String sIP = input.nextLine();
        try {
            networkingComponent.connectToIP(sIP);
        } catch (IOException e) {
            System.out.println("Error at connecting!");
            return false;
        }


        return true;
    }
}
