package GUI;
import GUI.GUI_Component;
import Resident_Daemon.CommandsPack.CommandExecutor;
import Resident_Daemon.CommandsPack.Commands.ChooseFolder;
import Resident_Daemon.CommandsPack.Commands.InviteToSwarm;
import Resident_Daemon.Core.Singleton;

import java.util.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.ArrayList;

// Resident Daemon IMPORTS, keep commented for now:
/*import Resident_Daemon.CommandsPack.Command;
import Resident_Daemon.CommandsPack.CommandExecutor;
import Resident_Daemon.CommandsPack.Commands.*;
import Resident_Daemon.Core.Singleton;*/
// --

public class WorkScreen extends JFrame implements ActionListener{
    private final GUI_Component frame;
    ConnectScreen connectScreen;
    // Declared elements:
    JButton syncButton;
    JButton disconnectButton;
    JButton quitButton;
    JPanel membersText;
    JPanel ipList;
    JButton changeSyncFolderButton;
    JButton inviteToSwarm;
    String activeFolderPath=null;
    JTextArea topText = new JTextArea("Members: ");
    List<String> invitedIPs;

    CommandExecutor commandExecutor;

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
                activeFolderPath=chooser.getSelectedFile().getAbsolutePath();
                commandExecutor.ExecuteOperation(new ChooseFolder(activeFolderPath));
                System.out.println(Singleton.getSingletonObject().getFolderToSyncPath());
            }
            else{
                //aici apare un pop-up cu No Selection.
            }
        }
        if(e.getSource()==inviteToSwarm){
            String invited=null;
            invited=JOptionPane.showInputDialog("Send invitation to IP:");
            invitedIPs.add(invited);
            System.out.println(invited);
            //Command invite=new InviteToSwarm(invited);
        }
        if (e.getSource() == syncButton) {
            //SyncSwarm(Integer swarmID, Integer peerID) i dont know what is the meaning of the parameters
            //Command sync = new SyncSwarm();
            //commandExecutor.ExecuteOperation(sync);
        }
        if (e.getSource() == disconnectButton) {
            //Command exit = new ExitApp();
            //commandExecutor.ExecuteOperation(exit);

            //add all the buttons etc
            frame.remove(syncButton);
            frame.remove(disconnectButton);
            frame.remove(quitButton);
            frame.remove(membersText);
            frame.remove(ipList);
            frame.revalidate();
            frame.repaint();
            connectScreen = new ConnectScreen(frame);
        }
        if (e.getSource() == quitButton) {
            //Command exit = new ExitApp();
            //commandExecutor.ExecuteOperation(exit);
            //exit(1);
        }
    }
    // --
    public WorkScreen(GUI_Component frame){
        this.frame = frame;

        // Declaration for use of Commands, keep commented for now:
         commandExecutor = Singleton.getSingletonObject().getCommandExecutor();
        // If you need to use a command from the Resident Daemon team, you must do the following steps:
        // 1. Declare the command of your choice like:
        // Command nameofyourchoice = new NameOfCommand();
        // 2. Execute it like so:
        // commandExecutor.ExecuteOperation(nameofyourchoice).
        // Some commands require you to insert data, others send you the data, look at the command
        // you have to implement to understand what it does.
        // --

        // Elements:
        // Change Sync Folder:
        changeSyncFolderButton = new JButton();
        changeSyncFolderButton.setText("Change Sync Folder");
        changeSyncFolderButton.setBounds(30,590,200,30);
        changeSyncFolderButton.setForeground(new Color(0x000000));
        changeSyncFolderButton.setBackground(new Color(0xB1B6A6));
        changeSyncFolderButton.setFocusable(false);
        changeSyncFolderButton.setFont(new Font("Radio Canada", Font.BOLD, 15));
        changeSyncFolderButton.addActionListener(this);
        changeSyncFolderButton.setAlignmentX(-1);


        // --
        // Invite To Swarm:
        inviteToSwarm = new JButton("Invite2Swarm");
        inviteToSwarm.setBounds(30,490,200,30);
        inviteToSwarm.setForeground(new Color(0x000000));
        inviteToSwarm.setBackground(new Color(0xB1B6A6));
        inviteToSwarm.setFocusable(false);
        inviteToSwarm.setFont(new Font("Radio Canada", Font.BOLD, 15));
        inviteToSwarm.addActionListener(this);
        inviteToSwarm.setAlignmentX(-1);
        // --
        // Sync Button:
        syncButton = new JButton();
        syncButton.setText("Sync with Swarm");
        syncButton.setBounds(420, 350, 300, 30);
        syncButton.setForeground(new Color(0x000000));
        syncButton.setBackground(new Color(0xB1B6A6));
        syncButton.setFocusable(false);
        syncButton.setFont(new Font("Radio Canada", Font.BOLD, 15));
        syncButton.addActionListener(this);
        // --
        // Logout Button:
        disconnectButton = new JButton();
        disconnectButton.setText("Logout");
        disconnectButton.setBounds(420, 450, 125, 30);
        disconnectButton.setForeground(new Color(0x000000));
        disconnectButton.setBackground(new Color(0xB1B6A6));
        disconnectButton.setFocusable(false);
        disconnectButton.setFont(new Font("Radio Canada", Font.BOLD, 15));
        disconnectButton.addActionListener(this);
        // --
        // Quit Button:
        quitButton = new JButton();
        quitButton.setText("Quit");
        quitButton.setBounds(595, 450, 125, 30);
        quitButton.setForeground(new Color(0x000000));
        quitButton.setBackground(new Color(0xB1B6A6));
        quitButton.setFocusable(false);
        quitButton.setFont(new Font("Radio Canada", Font.BOLD, 15));
        quitButton.addActionListener(this);
        // --
        // Members Text:
        membersText = new JPanel();
        membersText.setBounds(1050,50,200,50);
        membersText.setForeground(Color.BLACK);
        membersText.setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, Color.black));
        topText.setFont(new Font("Radio Canada", Font.ITALIC, 25));
        topText.setForeground(new Color(0x000000));
        topText.setBackground(new Color(0xB1B6A6));
        topText.setEditable(false);
        membersText.add(topText);
        // --
        // IP List:
        ipList = new JPanel();
        JPanel p = new JPanel(new GridBagLayout());
        ipList.add(new JScrollPane(p,
                JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER));
        GridBagConstraints gbc = new GridBagConstraints();
        ipList.setLayout(new BoxLayout(ipList, BoxLayout.PAGE_AXIS));
        ipList.setBounds(1050,98,200,525);
        ipList.setMaximumSize(new Dimension(100, 200));
        p.setFont(new Font("Radio Canada", Font.ITALIC, 25));
        p.setForeground(new Color(0x000000));
        p.setBackground(new Color(0xB1B6A6));
        ipList.setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, Color.black));
        ipList.setAutoscrolls(true);
        gbc.insets = new Insets(5,5,5,180);
        for (int ii=0; ii<100; ii++) {
            gbc.gridy = ii;
            gbc.gridx = 0;
            p.add(new JLabel("- ip  " + (ii+1)), gbc);
            gbc.gridx = 1;
        }
        // --
        // Added elements:
        frame.add(changeSyncFolderButton);
        frame.add(inviteToSwarm);
        frame.add(syncButton);
        frame.add(disconnectButton);
        frame.add(quitButton);
        frame.add(membersText);
        frame.add(ipList);
        // --
        frame.revalidate();
        frame.repaint();
    }
}
