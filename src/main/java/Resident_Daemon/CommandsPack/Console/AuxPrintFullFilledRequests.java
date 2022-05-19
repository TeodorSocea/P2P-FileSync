package Resident_Daemon.CommandsPack.Console;

import Resident_Daemon.CommandsPack.AuxiliarCommand;
import Resident_Daemon.CommandsPack.Command;
import Resident_Daemon.CommandsPack.Commands.PrintFullFilledRequests;
import Resident_Daemon.Core.Singleton;

public class AuxPrintFullFilledRequests implements AuxiliarCommand {

    @Override
    public Command run() {
        return new PrintFullFilledRequests();
    }
}
