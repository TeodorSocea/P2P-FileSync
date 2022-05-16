package Resident_Daemon.CommandsPack.Commands.Console;

import Networking.Core.NetworkingComponent;
import Resident_Daemon.CommandsPack.Commands.Command;
import Resident_Daemon.CommandsPack.Commands.ExceptionModule;
import Resident_Daemon.Core.Singleton;
import Resident_Daemon.Core.Input;

import java.io.IOException;

public class RespondToInvitation extends ExceptionModule implements Command {

    @Override
    public boolean execute() {
        NetworkingComponent networkingComponent = Singleton.getSingletonObject().getNetworkingComponent();

        Input.confScanner();

        System.out.println("Input the index of the invitation(PrintInvitations to identify the index): ");
        String sIndex = Input.nextLine();
        System.out.println("Respond! true/false?: ");
        String sResponse = Input.nextLine();


        try {
            int index = Integer.parseInt(sIndex);
            boolean response = Boolean.parseBoolean(sResponse);

            networkingComponent.respondToInvitationToSwarm(index, response);
        } catch (IOException e) {
            System.out.println("Error at responding!");
            setException(e);
            return false;
        } catch (NumberFormatException e) {
            System.out.println("Please enter a valid number!");
            setException(e);
            return false;
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Invalid index!");
            setException(e);
            return false;
        }

        return true;
    }
}
