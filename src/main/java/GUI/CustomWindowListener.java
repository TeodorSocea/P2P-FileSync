package GUI;

import Resident_Daemon.CommandsPack.CommandExecutor;
import Resident_Daemon.CommandsPack.Commands.ExitApp;
import Resident_Daemon.Core.Singleton;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

class CustomWindowListener implements WindowListener {

    public void windowClosing(WindowEvent arg0) {

        CommandExecutor commandExecutor = Singleton.getSingletonObject().getCommandExecutor();
        commandExecutor.ExecuteOperation(new ExitApp());
    }

    public void windowOpened(WindowEvent arg0) {}
    public void windowClosed(WindowEvent arg0) {}
    public void windowIconified(WindowEvent arg0) {}
    public void windowDeiconified(WindowEvent arg0) {}
    public void windowActivated(WindowEvent arg0) {}
    public void windowDeactivated(WindowEvent arg0) {}

}