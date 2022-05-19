package Resident_Daemon.CommandsPack.Console;

import Networking.Core.NetworkingComponent;
import Resident_Daemon.CommandsPack.AuxiliarCommand;
import Resident_Daemon.CommandsPack.Command;
import Resident_Daemon.CommandsPack.Commands.CreateSwarm;
import Resident_Daemon.Core.Singleton;
import Resident_Daemon._UnitTests.ExceptionModule;

public class AuxCreateSwarm implements AuxiliarCommand {

    @Override
    public Command run() {
        return new CreateSwarm();
    }
}
