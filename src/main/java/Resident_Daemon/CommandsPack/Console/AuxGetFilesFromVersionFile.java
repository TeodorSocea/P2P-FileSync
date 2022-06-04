package Resident_Daemon.CommandsPack.Console;

import Resident_Daemon.CommandsPack.AuxiliarCommand;
import Resident_Daemon.CommandsPack.Command;
import Resident_Daemon.CommandsPack.CommandExecutor;
import Resident_Daemon.CommandsPack.Commands.GetFilesFromVersionFile;
import Resident_Daemon.CommandsPack.Commands.InviteToSwarm;
import Resident_Daemon.CommandsPack.Commands.PrintIPs;
import Resident_Daemon.Core.Input;
import Resident_Daemon.Core.Singleton;
import Version_Control.Version_Control_Component;

public class AuxGetFilesFromVersionFile  implements AuxiliarCommand {

    private int GetSwarmID(){

        System.out.println("Input swarm's ID: ");

        String sID = Input.nextLine();
        int swarmID = Integer.parseInt(sID);

        return swarmID;
    }

    @Override
    public Command run() {

        try {
            int swarmID =  GetSwarmID();

            return new GetFilesFromVersionFile(swarmID);

        } catch (NumberFormatException e){

            System.out.println("Please enter a valid number!");
            return null;

        }
    }
}
