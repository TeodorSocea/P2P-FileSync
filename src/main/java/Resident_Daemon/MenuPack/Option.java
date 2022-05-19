package Resident_Daemon.MenuPack;

import Resident_Daemon.CommandsPack.CommandExecutor;
import Resident_Daemon.CommandsPack.AuxiliarCommand;
import Resident_Daemon.Core.Singleton;

class Option {
    private String whatToDisplay = null;
    private AuxiliarCommand whatToExecute = null; // HERE WE CAN HAVE MULTIPLE COMMANDS ON A CHOICE <<- A LIST OF COMMANDS "whatToExecute"
    private CommandExecutor commandExecutor;

    public Option(String whatToDisplay, AuxiliarCommand whatToExecute) {
        this.whatToDisplay = whatToDisplay;
        this.whatToExecute = whatToExecute;
        commandExecutor = Singleton.getSingletonObject().getCommandExecutor();
    }

    public String getWhatToDisplay() {
        if (this.whatToExecute == null) {
            return "NULL";
        }
        return this.whatToDisplay;
    }

    public AuxiliarCommand getWhatToExecute() {
        if (this.whatToExecute == null) {
            return () -> {
                System.out.println("NULL COMMAND!");
                return null;
            };
        }
        return this.whatToExecute;
    }
}
