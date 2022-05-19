package Resident_Daemon.CommandsPack.Console;

import Networking.Core.NetworkingComponent;
import Networking.Messages.InviteMessage;
import Resident_Daemon.CommandsPack.AuxiliarCommand;
import Resident_Daemon.CommandsPack.Command;
import Resident_Daemon.CommandsPack.Commands.InviteToSwarm;
import Resident_Daemon._UnitTests.ExceptionModule;
import Resident_Daemon.Core.Singleton;
import Resident_Daemon.Core.Input;
import org.apache.commons.lang3.AnnotationUtils;

import java.io.IOException;

public class AuxInviteToSwarm implements AuxiliarCommand {

    private String GetIP(){
        System.out.println("Input IP: ");
        String IP = Input.nextLine();
        return IP;
    }

    private int GetSwarmID(){

        System.out.println("Input swarm's ID: ");

        String sID = Input.nextLine();
        int swarmID = Integer.parseInt(sID);

        return swarmID;
    }

    @Override
    public Command run() {

        try {
            String IP = GetIP();
            int swarmID =  GetSwarmID();

            return new InviteToSwarm(IP, swarmID);

        } catch (NumberFormatException e){

            System.out.println("Please enter a valid number!");
            return null;

        }

    }
}
