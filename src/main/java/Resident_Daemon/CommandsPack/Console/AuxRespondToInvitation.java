package Resident_Daemon.CommandsPack.Console;

import Networking.Core.NetworkingComponent;
import Resident_Daemon.CommandsPack.AuxiliarCommand;
import Resident_Daemon.CommandsPack.Command;
import Resident_Daemon.CommandsPack.Commands.RespondToInvitation;
import Resident_Daemon._UnitTests.ExceptionModule;
import Resident_Daemon.Core.Singleton;
import Resident_Daemon.Core.Input;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class AuxRespondToInvitation implements AuxiliarCommand {

    private Integer GetInvitationIndex(){
        System.out.println("Input the index of the invitation(PrintInvitations to identify the index): ");
        String sIndex = Input.nextLine();

        return Integer.parseInt(sIndex);
    }

    private boolean GetInvitationResponse(){
        System.out.println("Respond! true/false?: ");
        String sResponse = Input.nextLine();

        return Boolean.parseBoolean(sResponse);
    }

    @Override
    public Command run() {

        try {
            int invIndex = GetInvitationIndex();
            boolean response = GetInvitationResponse();

            return new RespondToInvitation(invIndex, response);
        } catch (NumberFormatException | IndexOutOfBoundsException e) {
            if(e instanceof NumberFormatException){
                System.out.println("Please enter a valid number!");
            } else if(e instanceof IndexOutOfBoundsException){
                System.out.println("Invalid index!");

            }
            return null;
        }

    }
}
