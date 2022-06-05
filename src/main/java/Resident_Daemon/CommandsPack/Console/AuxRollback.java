package Resident_Daemon.CommandsPack.Console;

import Resident_Daemon.CommandsPack.AuxiliarCommand;
import Resident_Daemon.CommandsPack.Command;
import Resident_Daemon.CommandsPack.Commands.GetFilesFromVersionFile;
import Resident_Daemon.CommandsPack.Commands.Rollback;
import Resident_Daemon.Core.Input;

public class AuxRollback implements AuxiliarCommand {

    private String GetFileName(){
        System.out.println("Input fileName: ");
        String fileName = Input.nextLine();
        return fileName;
    }

    private long GetTimestamp(){

        System.out.println("Input timestamp: ");

        String timestamp_str = Input.nextLine();
        long timestamp = Long.parseLong(timestamp_str);

        return timestamp;
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
            String fileName = GetFileName();
            long timestamp = GetTimestamp();
            int swarmID = GetSwarmID();

            return new Rollback(fileName, timestamp, swarmID);

        } catch (NumberFormatException e){

            System.out.println("Please enter a valid number!");
            return null;

        }
    }
}
