package Resident_Daemon.CommandsPack.Console;

import Resident_Daemon.CommandsPack.Command;
import Resident_Daemon._UnitTests.ExceptionModule;
import Resident_Daemon.Core.Singleton;

import java.io.IOException;

import static java.lang.System.exit;

public class ExitApp extends ExceptionModule implements Command {

    @Override
    public boolean execute() {

        try {
            Singleton.saveSingletonData();
        } catch (IOException e) {
            e.printStackTrace();
        }

        exit(0);

        return true;
    }
}
