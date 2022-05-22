package GUI;
import GUI.GUI_Component;
import Resident_Daemon.CommandsPack.Commands.Command;
import Resident_Daemon.CommandsPack.Commands.Console.InviteToSwarm;

import javax.swing.JOptionPane;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

// Resident Daemon IMPORTS, keep commented for now:
/*import Resident_Daemon.CommandsPack.Command;
import Resident_Daemon.CommandsPack.CommandExecutor;
import Resident_Daemon.CommandsPack.Commands.*;
import Resident_Daemon.Core.Singleton;*/
// --

public class WorkScreen extends JFrame implements ActionListener{
    private final GUI_Component frame;
    // Declared elements:
    JButton changeSyncFolderButton;
    JButton chooseFileToSync;
    JButton inviteToSwarm;
    String activeFolderPath=null;
    String activeFilePath=null;
    List<String> invitedIPs;
    // --
    // Button functions:
    public void actionPerformed(ActionEvent e) {
        if (e.getSource()==changeSyncFolderButton){
            JFileChooser chooser = new JFileChooser();
            chooser.setCurrentDirectory(new java.io.File("."));
            chooser.setDialogTitle("Change Sync Folder");
            chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            chooser.setAcceptAllFileFilterUsed(false);
            if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
                activeFolderPath=chooser.getCurrentDirectory().getAbsolutePath();
                System.out.println(activeFolderPath);
            }
            else{
                //aici apare un pop-up cu No Selection.
            }
        }
        if(e.getSource()==chooseFileToSync){
            JFileChooser chooser = new JFileChooser();
            chooser.setCurrentDirectory(new java.io.File(activeFolderPath));
            chooser.setDialogTitle("Choose file to sync");
            if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION){
                activeFilePath=chooser.getSelectedFile().getAbsolutePath();
                System.out.println(activeFilePath);
            }
        }
        if(e.getSource()==inviteToSwarm){
            String invited=null;
            invited=JOptionPane.showInputDialog("Send invitation to IP:");
            invitedIPs.add(invited);
            System.out.println(invited);
            //Command invite=new InviteToSwarm(invited);
        }
    }
    // --
    public WorkScreen(GUI_Component frame){
        this.frame = frame;
        invitedIPs=new ArrayList<String>();

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

        //changeSyncFolder
        changeSyncFolderButton=new JButton();
        changeSyncFolderButton.setText("Change Sync Folder");
        changeSyncFolderButton.setBounds(30,590,200,30);
        changeSyncFolderButton.setForeground(new Color(0x000000));
        changeSyncFolderButton.setBackground(new Color(0xB1B6A6));
        changeSyncFolderButton.setFocusable(false);
        changeSyncFolderButton.setFont(new Font("Radio Canada", Font.BOLD, 15));
        changeSyncFolderButton.addActionListener(this);
        changeSyncFolderButton.setAlignmentX(-1);

        //chooseFileToSync
        chooseFileToSync=new JButton();
        chooseFileToSync.setText("Choose file to sync");
        chooseFileToSync.setBounds(30,540,200,30);
        chooseFileToSync.setForeground(new Color(0x000000));
        chooseFileToSync.setBackground(new Color(0xB1B6A6));
        chooseFileToSync.setFocusable(false);
        chooseFileToSync.setFont(new Font("Radio Canada", Font.BOLD, 15));
        chooseFileToSync.addActionListener(this);
        chooseFileToSync.setAlignmentX(-1);

        //inviteToSwarm:
        inviteToSwarm=new JButton("Invite2Swarm");
        inviteToSwarm.setBounds(30,490,200,30);
        inviteToSwarm.setForeground(new Color(0x000000));
        inviteToSwarm.setBackground(new Color(0xB1B6A6));
        inviteToSwarm.setFocusable(false);
        inviteToSwarm.setFont(new Font("Radio Canada", Font.BOLD, 15));
        inviteToSwarm.addActionListener(this);
        inviteToSwarm.setAlignmentX(-1);
        // --
        // Added elements:
          frame.add(changeSyncFolderButton);
          frame.add(chooseFileToSync);
          frame.add(inviteToSwarm);
        // --
        frame.revalidate();
        frame.repaint();
    }
}
