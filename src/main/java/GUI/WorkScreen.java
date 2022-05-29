package GUI;
import GUI.GUI_Component;
import Networking.Core.NetworkingComponent;
import Networking.Peer.Peer;
import Networking.Swarm.NetworkSwarm;
import Resident_Daemon.CommandsPack.CommandExecutor;
import Resident_Daemon.CommandsPack.Commands.ChooseFolder;
import Resident_Daemon.CommandsPack.Commands.ExitApp;
import Resident_Daemon.CommandsPack.Commands.InviteToSwarm;
import Resident_Daemon.CommandsPack.Commands.SyncSwarm;
import Resident_Daemon.Core.Singleton;
import Resident_Daemon.Core.UserData;

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
    JLabel swarmInfo;
    JLabel yourIp;
    JButton changeSyncFolderButton;
    JButton inviteToSwarm;
    JButton[] ipList;
    JPanel filesTab;
    JButton[] filesList;
    String activeFolderPath=null;
    int currentSwarmId;
    int selectedIP;

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

        /*if (ipList != null) {
            for (int i = 0; i < ipList.length; i++) {
                if (e.getSource() == ipList[i]) {
                    selectedIP = Integer.parseInt(ipList[i].getText());
                    System.out.println("// SWARM IP PRESSED: " + ipList[i].getText());
                    syncButton.setText("Sync?: " + ipList[i].getText());
                    frame.revalidate();
                    frame.repaint();
                }
            }
        }*/

        if (filesList != null) {
            for (int i = 0; i < filesList.length; i++) {
                if (e.getSource() == filesList[i]) {
                    // selectedIP = filesList[i].getText();
                    System.out.println("// FILE INFO BUTTON PRESSED: " + filesList[i].getText());
                    frame.revalidate();
                    frame.repaint();
                }
            }
        }

        if (e.getSource() == syncButton) {
            System.out.print(syncButton.getText());
            // commandExecutor.ExecuteOperation(new SyncSwarm(currentSwarmId, selectedIP));
            syncButton.setText("Synced. Tap again to sync");
            frame.revalidate();
            frame.repaint();
        }

        if (e.getSource() == disconnectButton) {
            frame.remove(syncButton);
            frame.remove(disconnectButton);
            frame.remove(quitButton);
            frame.remove(inviteToSwarm);
            frame.remove(changeSyncFolderButton);
            // frame.remove(ipTab);
            frame.remove(yourIp);
            frame.remove(swarmInfo);
            frame.remove(filesTab);
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
    /*public void ipGenerator() {
        ipTab = new JPanel();
        JPanel p = new JPanel(new GridBagLayout());
        ipTab.add(new JScrollPane(p, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER));
        GridBagConstraints gbc = new GridBagConstraints();
        ipTab.setLayout(new BoxLayout(ipTab, BoxLayout.PAGE_AXIS));
        ipTab.setBounds(770,118,160,425);
        ipTab.setMaximumSize(new Dimension(100, 200));
        p.setFont(new Font("Radio Canada", Font.ITALIC, 25));
        p.setForeground(new Color(0x000000));
        p.setBackground(new Color(0xB1B6A6));
        ipTab.setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, Color.black));
        ipTab.setAutoscrolls(true);
        ipList = new JButton[10];
        gbc.insets = new Insets(5,5,5,180);
        int ji = 0;
        NetworkSwarm temp = null;
        for (Map.Entry<Integer, Peer> swarmEntry : temp.getPeers().entrySet()) {
            gbc.gridy = ji;
            gbc.gridx = 0;
            ipList[ji] = new JButton(swarmEntry.getValue().toString() + ji);
            p.add(ipList[ji], gbc);
            ipList[ji].addActionListener(this);
            gbc.gridx = 1;
            ji++;
        }
        frame.add(ipTab);
    }
*/
    // File list function:
    public void fileListGenerator() {
        filesTab = new JPanel();
        JPanel m = new JPanel(new GridBagLayout());
        filesTab.add(new JScrollPane(m, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER));
        GridBagConstraints gbcc = new GridBagConstraints();
        filesTab.setLayout(new BoxLayout(filesTab, BoxLayout.PAGE_AXIS));
        filesTab.setBounds(350,118,160,425);
        filesTab.setMaximumSize(new Dimension(100, 200));
        m.setFont(new Font("Radio Canada", Font.ITALIC, 25));
        m.setForeground(new Color(0x000000));
        m.setBackground(new Color(0xB1B6A6));
        filesTab.setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, Color.black));
        filesTab.setAutoscrolls(true);
        filesList = new JButton[10];
        gbcc.insets = new Insets(5,5,5,180);
        // int ji = 0;
        for (int ji = 0; ji < 10; ji++) {
            // for (var swarmEntry : userData.getMySwarms().entrySet()) {
            // NetworkSwarm temp = swarmEntry.getValue();
            gbcc.gridy = ji;
            gbcc.gridx = 0;
            filesList[ji] = new JButton("file " + ji + ".smth");
            m.add(filesList[ji], gbcc);
            filesList[ji].addActionListener(this);
            gbcc.gridx = 1;
            ji++;
        }
        frame.add(filesTab);
    }

    public WorkScreen(GUI_Component frame, int readSwarmId){
        this.frame = frame;
        currentSwarmId = readSwarmId;
        commandExecutor = Singleton.getSingletonObject().getCommandExecutor();

        // Current IP:
        yourIp = new JLabel();
        yourIp.setText("Your ID: ");
        yourIp.setForeground(new Color(0xB1B6A6));
        yourIp.setFont(new Font("Radio Canada", Font.BOLD, 16));
        yourIp.setBounds(550, 200, 200, 30);

        // Current Swarm:
        swarmInfo = new JLabel();
        swarmInfo.setText("Swarm ID: " + currentSwarmId);
        swarmInfo.setForeground(new Color(0xB1B6A6));
        swarmInfo.setFont(new Font("Radio Canada", Font.BOLD, 16));
        swarmInfo.setBounds(585, 230, 200, 30);

        // Change Sync Folder:
        changeSyncFolderButton = new JButton();
        changeSyncFolderButton.setText("Change Sync Folder");
        changeSyncFolderButton.setBounds(530,330,220,30);
        changeSyncFolderButton.setForeground(new Color(0x000000));
        changeSyncFolderButton.setBackground(new Color(0xB1B6A6));
        changeSyncFolderButton.setFocusable(false);
        changeSyncFolderButton.setFont(new Font("Radio Canada", Font.BOLD, 15));
        changeSyncFolderButton.addActionListener(this);
        changeSyncFolderButton.setAlignmentX(-1);

        // Invite To Swarm:
        inviteToSwarm = new JButton("Invite to Swarm");
        inviteToSwarm.setBounds(530,370,220,30);
        inviteToSwarm.setForeground(new Color(0x000000));
        inviteToSwarm.setBackground(new Color(0xB1B6A6));
        inviteToSwarm.setFocusable(false);
        inviteToSwarm.setFont(new Font("Radio Canada", Font.BOLD, 15));
        inviteToSwarm.addActionListener(this);
        inviteToSwarm.setAlignmentX(-1);

        // Sync Button:
        syncButton = new JButton();
        syncButton.setText("Press to Sync");
        syncButton.setBounds(530, 290, 220, 30);
        syncButton.setForeground(new Color(0x000000));
        syncButton.setBackground(new Color(0xB1B6A6));
        syncButton.setFocusable(false);
        syncButton.setFont(new Font("Radio Canada", Font.BOLD, 15));
        syncButton.addActionListener(this);

        // Logout Button:
        disconnectButton = new JButton();
        disconnectButton.setText("Logout");
        disconnectButton.setBounds(530, 410, 100, 30);
        disconnectButton.setForeground(new Color(0x000000));
        disconnectButton.setBackground(new Color(0xB1B6A6));
        disconnectButton.setFocusable(false);
        disconnectButton.setFont(new Font("Radio Canada", Font.BOLD, 15));
        disconnectButton.addActionListener(this);

        // Quit Button:
        quitButton = new JButton();
        quitButton.setText("Quit");
        quitButton.setBounds(650, 410, 100, 30);
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
        frame.add(yourIp);
        frame.add(swarmInfo);
        // ipGenerator();
        fileListGenerator();

        // --
        frame.revalidate();
        frame.repaint();
    }
}
