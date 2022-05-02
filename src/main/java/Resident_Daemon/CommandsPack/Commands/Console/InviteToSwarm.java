package Resident_Daemon.CommandsPack.Commands.Console;

import Networking.Core.NetworkingComponent;
import Resident_Daemon.CommandsPack.Commands.Command;
import Resident_Daemon.Singleton;

import java.io.IOException;
import java.util.Scanner;

public class InviteToSwarm implements Command {

    @Override
    public boolean execute() {
        NetworkingComponent networkingComponent = Singleton.getSingletonObject().getNetworkingComponent();

        Scanner input = new Scanner(System.in);

        System.out.println("Input IP: ");
        String sIP = input.nextLine();
        System.out.println("Input swarm's ID: ");
        String sID = input.nextLine();


        try {
            int ID = Integer.parseInt(sID);
            networkingComponent.inviteIPToSwarm(sIP, ID);
        } catch (IOException e) {
            System.out.println("Error at inviting!");
            return false;
        } catch (NumberFormatException e) {
            System.out.println("Please enter a valid number!");
            return false;
        }

        return true;
    }
}
