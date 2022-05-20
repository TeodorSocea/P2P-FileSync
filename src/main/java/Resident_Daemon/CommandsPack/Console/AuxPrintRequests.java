package Resident_Daemon.CommandsPack.Console;

import Resident_Daemon.CommandsPack.AuxiliarCommand;
import Resident_Daemon.CommandsPack.Command;
import Resident_Daemon.CommandsPack.Commands.PrintRequests;

public class AuxPrintRequests implements AuxiliarCommand {

    @Override
    public Command run() {
        return new PrintRequests();
    }
}
