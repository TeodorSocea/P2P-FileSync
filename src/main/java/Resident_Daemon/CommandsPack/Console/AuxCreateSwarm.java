package Resident_Daemon.CommandsPack.Console;

import Networking.Core.NetworkingComponent;
import Resident_Daemon.CommandsPack.AuxiliarCommand;
import Resident_Daemon.CommandsPack.Command;
import Resident_Daemon.CommandsPack.Commands.CreateSwarm;
import Resident_Daemon.Core.Input;
import Resident_Daemon.Core.Singleton;
import Resident_Daemon._UnitTests.ExceptionModule;

public class AuxCreateSwarm implements AuxiliarCommand {

    private String GetSwarmName(){
        System.out.println("Input swarm name: ");
        String swarmName = Input.nextLine();
        return swarmName;
    }

    @Override
    public Command run() {
        return new CreateSwarm(GetSwarmName());
    }
}
