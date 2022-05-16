package Resident_Daemon.CommandsPack.Commands.Console;

import Networking.Core.NetworkingComponent;
import Resident_Daemon.CommandsPack.Commands.Command;
import Resident_Daemon.CommandsPack.Commands.ExceptionModule;
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
