package GUI;
import GUI.GUI_Component;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// Resident Daemon IMPORTS, keep commented for now:
/*import Resident_Daemon.CommandsPack.Command;
import Resident_Daemon.CommandsPack.CommandExecutor;
import Resident_Daemon.CommandsPack.Commands.*;
import Resident_Daemon.Core.Singleton;*/
// --

public class WorkScreen extends JFrame implements ActionListener{
    private final GUI_Component frame;
    // Declared elements:

    // --
    // Button functions:
    public void actionPerformed(ActionEvent e) {

    }
    // --
    public WorkScreen(GUI_Component frame){
        this.frame = frame;

        // Declaration for use of Commands, keep commented for now:
        // CommandExecutor commandExecutor = Singleton.getSingletonObject().getCommandExecutor();
        // If you need to use a command from the Resident Daemon team, you must do the following steps:
        // 1. Declare the command of your choice like:
        // Command nameofyourchoice = new NameOfCommand();
        // 2. Execute it like so:
        // commandExecutor.ExecuteOperation(nameofyourchoice).
        // Some commands require you to insert data, others send you the data, look at the command
        // you have to implement to understand what it does.
        // --

        // Elements:

        // --
        // Added elements:

        // --
        frame.revalidate();
        frame.repaint();
    }
}
