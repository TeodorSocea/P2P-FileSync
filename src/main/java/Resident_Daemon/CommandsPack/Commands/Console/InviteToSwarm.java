package Resident_Daemon.CommandsPack.Commands.Console;

import Networking.Core.NetworkingComponent;
import Resident_Daemon.CommandsPack.Commands.Command;
import Resident_Daemon._UnitTests.ExceptionModule;
import Resident_Daemon.Core.Singleton;
import Resident_Daemon.Core.Input;

import java.io.IOException;

public class InviteToSwarm extends ExceptionModule implements Command {

    @Override
    public boolean execute() {
        NetworkingComponent networkingComponent = Singleton.getSingletonObject().getNetworkingComponent();

        Input.confScanner();

        System.out.println("Input IP: ");
        String sIP = Input.nextLine();
        System.out.println("Input swarm's ID: ");
        String sID = Input.nextLine();


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
