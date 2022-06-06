package Resident_Daemon.CommandsPack.Commands;

import Resident_Daemon.CommandsPack.Command;
import Resident_Daemon.Core.Singleton;
import Resident_Daemon._UnitTests.ExceptionModule;

import java.io.IOException;

import static java.lang.System.exit;

public class ExitApp extends ExceptionModule implements Command {

    @Override
    public boolean execute() {

        try {
            Singleton.saveSingletonData();
            System.out.println("Saved folder path!");
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("App closing...");
        exit(0);

        return true;
    }
}
