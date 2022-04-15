package Resident_Daemon.CommandsPack;

import Resident_Daemon.CommandsPack.Commands.Command;

import java.util.ArrayList;
import java.util.List;

public class CommandExecutor {
    private List<Command> commandsList;

    public CommandExecutor() {
        commandsList = new ArrayList<>();
    }

    public boolean ExecuteOperation(Command command){
        commandsList.add(command);
        return command.execute();
    }
}
