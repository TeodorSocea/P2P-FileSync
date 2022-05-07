package Resident_Daemon.CommandsPack.Commands.Console;

import Networking.Core.NetworkingComponent;
import Resident_Daemon.CommandsPack.Commands.Command;
import Resident_Daemon.Core.Singleton;

import java.io.IOException;
import java.util.Scanner;

public class RespondToInvitation implements Command {

    @Override
    public boolean execute() {
        NetworkingComponent networkingComponent = Singleton.getSingletonObject().getNetworkingComponent();

        Scanner input = new Scanner(System.in);

        System.out.println("Input the index of the invitation(PrintInvitations to identify the index): ");
        String sIndex = input.nextLine();
        System.out.println("Respond! true/false?: ");
        String sResponse = input.nextLine();


        try {
            int index = Integer.parseInt(sIndex);
            boolean response = Boolean.parseBoolean(sResponse);

            networkingComponent.respondToInvitationToSwarm(index, response);
        } catch (IOException e) {
            System.out.println("Error at responding!");
            return false;
        } catch (NumberFormatException e) {
            System.out.println("Please enter a valid number!");
            return false;
        }

        return true;
    }
}
