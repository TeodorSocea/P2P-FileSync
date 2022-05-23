package GUI;
import GUI.GUI_Component;
import Networking.Swarm.NetworkSwarm;
import Resident_Daemon.CommandsPack.CommandExecutor;
import Resident_Daemon.CommandsPack.Commands.ChooseFolder;
import Resident_Daemon.CommandsPack.Commands.ExitApp;
import Resident_Daemon.CommandsPack.Commands.InviteToSwarm;
import Resident_Daemon.CommandsPack.Commands.SyncSwarm;
import Resident_Daemon.Core.Singleton;

import java.util.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.ArrayList;

public class WorkScreen extends JFrame implements ActionListener{
    private final GUI_Component frame;
    ConnectScreen connectScreen;
    // Declared elements:
    JButton syncButton;
    JButton disconnectButton;
    JButton quitButton;
    JPanel ipTab;
    JButton changeSyncFolderButton;
    JButton inviteToSwarm;
    JButton[] ipList;
    String activeFolderPath=null;
    int currentSwarmId;
    String selectedIP;

    CommandExecutor commandExecutor;

    // Button functions:
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == changeSyncFolderButton){
            JFileChooser chooser = new JFileChooser();
            chooser.setCurrentDirectory(new java.io.File("."));
            chooser.setDialogTitle("Change Sync Folder");
            chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            chooser.setAcceptAllFileFilterUsed(false);
            if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
                activeFolderPath = chooser.getSelectedFile().getAbsolutePath();
                commandExecutor.ExecuteOperation(new ChooseFolder(activeFolderPath));
                System.out.println(Singleton.getSingletonObject().getFolderToSyncPath());
            }
            else{
                //aici apare un pop-up cu No Selection.
            }
        }

        if (e.getSource() == inviteToSwarm){
            String invited = null;
            invited = JOptionPane.showInputDialog("Send invitation to IP:");
            System.out.println(invited);
            commandExecutor.ExecuteOperation(new InviteToSwarm(invited, currentSwarmId));
        }

        if (ipList != null) {
            for (int i = 0; i < ipList.length; i++) {
                if (e.getSource() == ipList[i]) {
                    selectedIP = ipList[i].getText();
                    System.out.println("// SWARM IP PRESSED: " + ipList[i].getText());
                    syncButton.setText("Sync?: " + selectedIP);
                    frame.revalidate();
                    frame.repaint();
                }
            }
        }

        if (e.getSource() == syncButton) {
            System.out.print(selectedIP);
            // commandExecutor.ExecuteOperation(new SyncSwarm(currentSwarmId, selectedIP));
            syncButton.setText("Synced");
            frame.revalidate();
            frame.repaint();
        }

        if (e.getSource() == disconnectButton) {
            frame.remove(syncButton);
            frame.remove(disconnectButton);
            frame.remove(quitButton);
            frame.remove(inviteToSwarm);
            frame.remove(changeSyncFolderButton);
            frame.remove(ipTab);
            frame.revalidate();
            frame.repaint();
            connectScreen = new ConnectScreen(frame);
        }

        if (e.getSource() == quitButton) {
            commandExecutor.ExecuteOperation(new ExitApp());
            System.exit(1);
        }
    }

    // IP List function:
    public void ipGenerator() {
        ipTab = new JPanel();
        JPanel p = new JPanel(new GridBagLayout());
        ipTab.add(new JScrollPane(p, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER));
        GridBagConstraints gbc = new GridBagConstraints();
        ipTab.setLayout(new BoxLayout(ipTab, BoxLayout.PAGE_AXIS));
        ipTab.setBounds(770,118,200,425);
        ipTab.setMaximumSize(new Dimension(100, 200));
        p.setFont(new Font("Radio Canada", Font.ITALIC, 25));
        p.setForeground(new Color(0x000000));
        p.setBackground(new Color(0xB1B6A6));
        ipTab.setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, Color.black));
        ipTab.setAutoscrolls(true);
        ipList = new JButton[10];
        gbc.insets = new Insets(5,5,5,180);
        // int ji = 0;
        for (int ji = 0; ji < 10; ji++) {
        // for (var swarmEntry : userData.getMySwarms().entrySet()) {
            // NetworkSwarm temp = swarmEntry.getValue();
            gbc.gridy = ji;
            gbc.gridx = 0;
            ipList[ji] = new JButton("999.999.999.90" + ji);
            p.add(ipList[ji], gbc);
            ipList[ji].addActionListener(this);
            gbc.gridx = 1;
            ji++;
        }
        frame.add(ipTab);
    }

    public WorkScreen(GUI_Component frame, int readSwarmId){
        this.frame = frame;
        currentSwarmId = readSwarmId;
        commandExecutor = Singleton.getSingletonObject().getCommandExecutor();

        changeSyncFolderButton = new JButton();
        changeSyncFolderButton.setText("Change Sync Folder");
        changeSyncFolderButton.setBounds(30,590,200,30);
        changeSyncFolderButton.setForeground(new Color(0x000000));
        changeSyncFolderButton.setBackground(new Color(0xB1B6A6));
        changeSyncFolderButton.setFocusable(false);
        changeSyncFolderButton.setFont(new Font("Radio Canada", Font.BOLD, 15));
        changeSyncFolderButton.addActionListener(this);
        changeSyncFolderButton.setAlignmentX(-1);

        // Invite To Swarm:
        inviteToSwarm = new JButton("Invite2Swarm");
        inviteToSwarm.setBounds(30,490,200,30);
        inviteToSwarm.setForeground(new Color(0x000000));
        inviteToSwarm.setBackground(new Color(0xB1B6A6));
        inviteToSwarm.setFocusable(false);
        inviteToSwarm.setFont(new Font("Radio Canada", Font.BOLD, 15));
        inviteToSwarm.addActionListener(this);
        inviteToSwarm.setAlignmentX(-1);

        // Sync Button:
        syncButton = new JButton();
        syncButton.setText("Select IP to Sync");
        syncButton.setBounds(770, 543, 200, 30);
        syncButton.setForeground(new Color(0x000000));
        syncButton.setBackground(new Color(0xB1B6A6));
        syncButton.setFocusable(false);
        syncButton.setFont(new Font("Radio Canada", Font.BOLD, 15));
        syncButton.addActionListener(this);

        // Logout Button:
        disconnectButton = new JButton();
        disconnectButton.setText("Logout");
        disconnectButton.setBounds(420, 450, 125, 30);
        disconnectButton.setForeground(new Color(0x000000));
        disconnectButton.setBackground(new Color(0xB1B6A6));
        disconnectButton.setFocusable(false);
        disconnectButton.setFont(new Font("Radio Canada", Font.BOLD, 15));
        disconnectButton.addActionListener(this);

        // Quit Button:
        quitButton = new JButton();
        quitButton.setText("Quit");
        quitButton.setBounds(595, 450, 125, 30);
        quitButton.setForeground(new Color(0x000000));
        quitButton.setBackground(new Color(0xB1B6A6));
        quitButton.setFocusable(false);
        quitButton.setFont(new Font("Radio Canada", Font.BOLD, 15));
        quitButton.addActionListener(this);

        // Added elements:
        frame.add(changeSyncFolderButton);
        frame.add(inviteToSwarm);
        frame.add(syncButton);
        frame.add(disconnectButton);
        frame.add(quitButton);
        ipGenerator();

        // --
        frame.revalidate();
        frame.repaint();
    }
}
