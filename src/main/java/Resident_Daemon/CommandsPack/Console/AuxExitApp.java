package Resident_Daemon.CommandsPack.Console;

import Resident_Daemon.CommandsPack.AuxiliarCommand;
import Resident_Daemon.CommandsPack.Command;
import Resident_Daemon.CommandsPack.Commands.ExitApp;
import Resident_Daemon._UnitTests.ExceptionModule;
import Resident_Daemon.Core.Singleton;

import java.io.IOException;

import static java.lang.System.exit;

public class AuxExitApp implements AuxiliarCommand {

    @Override
    public Command run() {
        return new ExitApp();
    }
}
