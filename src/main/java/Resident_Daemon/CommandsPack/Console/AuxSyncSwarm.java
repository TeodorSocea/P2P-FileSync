package Resident_Daemon.CommandsPack.Console;

import Networking.Core.NetworkingComponent;
import Resident_Daemon.CommandsPack.AuxiliarCommand;
import Resident_Daemon.CommandsPack.Command;
import Resident_Daemon.CommandsPack.Commands.SyncSwarm;
import Resident_Daemon.Core.Input;
import Resident_Daemon.Core.Singleton;
import Resident_Daemon._UnitTests.ExceptionModule;

public class AuxSyncSwarm implements AuxiliarCommand {

    @Override
    public Command run() {

        try {
            System.out.println("SwarmID: ");
            int swarmID = Integer.parseInt(Input.nextLine());


            return new SyncSwarm(swarmID);

        } catch (Exception e){
            e.printStackTrace();
            return null;
        }

    }
}
