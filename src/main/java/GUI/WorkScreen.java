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

public class WorkScreen extends JFrame {
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
    String selectedIP;

    CommandExecutor commandExecutor;

    /* Button functions:
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
        /*if (filesList != null) {
            for (int i = 0; i < filesList.length; i++) {
                if (e.getSource() == filesList[i]) {
                    // selectedIP = filesList[i].getText();
                    System.out.println("// FILE INFO BUTTON PRESSED: " + filesList[i].getText());
                    frame.revalidate();
                    frame.repaint();
                }
            }
        }*/
    // IP List function:
    // Afisam IP-urile din zona, apasam pe un IP si dam send invitation.
    //
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

    public WorkScreen(GUI_Component frame, int readSwarmId){
        this.frame = frame;
        currentSwarmId = readSwarmId;
        commandExecutor = Singleton.getSingletonObject().getCommandExecutor();

        fileListTab();

        changeSyncFolderTab();

        inviteToSwarmTab();

        syncButton();

        miscElements();

        frame.revalidate();
        frame.repaint();
    }

    // fileListTab:
    private void fileListTab() {
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
            gbcc.gridy = ji;
            gbcc.gridx = 0;
            filesList[ji] = new JButton("file " + ji + ".smth");
            m.add(filesList[ji], gbcc);

            int i = ji;

            filesList[ji].addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    System.out.println("// FILE INFO BUTTON PRESSED: " + filesList[i].getText());
                    frame.revalidate();
                    frame.repaint();
                }
            });

            gbcc.gridx = 1;
            // ji++;
        }

        frame.add(filesTab);

    }


    // changeSyncFolderTab:
    private void changeSyncFolderTab() {
        changeSyncFolderButton = new JButton();

        changeSyncFolderButton.setText("Change Sync Folder");
        changeSyncFolderButton.setFont(new Font("Radio Canada", Font.BOLD, 15));

        changeSyncFolderButton.setForeground(new Color(0x000000));
        changeSyncFolderButton.setBackground(new Color(0xB1B6A6));

        changeSyncFolderButton.setBounds(530,330,220,30);
        changeSyncFolderButton.setFocusable(false);

        changeSyncFolderButton.setAlignmentX(-1);
        changeSyncFolderButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
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
        });

        frame.add(changeSyncFolderButton);
    }


    // inviteToSwarmTab:
    private void inviteToSwarmTab() {
        inviteToSwarm = new JButton("Select IP to invite");
        inviteToSwarm.setFont(new Font("Radio Canada", Font.BOLD, 15));

        inviteToSwarm.setBounds(530,370,220,30);
        inviteToSwarm.setFocusable(false);

        inviteToSwarm.setForeground(new Color(0x000000));
        inviteToSwarm.setBackground(new Color(0xB1B6A6));

        inviteToSwarm.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // commandExecutor.ExecuteOperation(InviteToSwarm(selectedIP, currentSwarmId));
                inviteToSwarm.setText("Invited.");
            }
        });

        inviteToSwarm.setAlignmentX(-1);

        ipTab = new JPanel();
        JPanel p = new JPanel(new GridBagLayout());

        ipTab.add(new JScrollPane(p, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER));
        ipTab.setLayout(new BoxLayout(ipTab, BoxLayout.PAGE_AXIS));

        ipTab.setBounds(770,118,160,425);
        ipTab.setMaximumSize(new Dimension(100, 200));

        p.setFont(new Font("Radio Canada", Font.ITALIC, 25));
        p.setForeground(new Color(0x000000));
        p.setBackground(new Color(0xB1B6A6));

        ipTab.setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, Color.black));
        ipTab.setAutoscrolls(true);

        ipList = new JButton[10];

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5,5,5,180);

        // int ji = 0;
        NetworkSwarm temp = null;
        for (int ji = 0; ji < 10; ji++) {
        // for (Map.Entry<Integer, Peer> swarmEntry : temp.getPeers().entrySet()) {
            gbc.gridy = ji;
            gbc.gridx = 0;
            // ipList[ji] = new JButton(swarmEntry.getValue().toString() + ji);
            ipList[ji] = new JButton("999.999.999.90" + ji);
            p.add(ipList[ji], gbc);
            int i = ji;
            ipList[ji].addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    selectedIP = ipList[i].getText();
                    System.out.println("GUI: Selected invite for " + selectedIP + ". Awaiting confirmation.");
                    inviteToSwarm.setText("Invite?: " + selectedIP);
                    frame.revalidate();
                    frame.repaint();
                }
            });
            gbc.gridx = 1;
            // ji++;
        }

        frame.add(inviteToSwarm);
        frame.add(ipTab);

    }


    // syncButton:
    private void syncButton() {
        syncButton = new JButton();

        syncButton.setText("Press to Sync");
        syncButton.setFont(new Font("Radio Canada", Font.BOLD, 15));

        syncButton.setForeground(new Color(0x000000));
        syncButton.setBackground(new Color(0xB1B6A6));

        syncButton.setBounds(530, 290, 220, 30);
        syncButton.setFocusable(false);

        syncButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.print("GUI: Synced.");
                // commandExecutor.ExecuteOperation(new SyncSwarm(currentSwarmId)); // fara selected;
                syncButton.setText("Synced. Tap again to sync");
                frame.revalidate();
                frame.repaint();
            }
        });

        frame.add(syncButton);
    }


    // yourIp, swarmInfo, dcButton, quitButton:
    private void miscElements() {
        yourIp = new JLabel();

        // yourIp settings:
        yourIp.setText("Your Ip: ");
        yourIp.setFont(new Font("Radio Canada", Font.BOLD, 16));

        yourIp.setForeground(new Color(0xB1B6A6));
        yourIp.setBounds(550, 200, 200, 30);


        swarmInfo = new JLabel();

        // swarmInfo settings:
        swarmInfo.setText("Swarm ID: " + currentSwarmId);
        swarmInfo.setFont(new Font("Radio Canada", Font.BOLD, 16));

        swarmInfo.setForeground(new Color(0xB1B6A6));
        swarmInfo.setBounds(585, 230, 200, 30);


        disconnectButton = new JButton();

        // disconnectButton settings:
        disconnectButton.setText("Logout");
        disconnectButton.setFont(new Font("Radio Canada", Font.BOLD, 15));

        disconnectButton.setForeground(new Color(0x000000));
        disconnectButton.setBackground(new Color(0xB1B6A6));

        disconnectButton.setBounds(530, 410, 100, 30);
        disconnectButton.setFocusable(false);

        // disconnectButton function:
        disconnectButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.remove(syncButton);
                frame.remove(disconnectButton);
                frame.remove(quitButton);
                frame.remove(inviteToSwarm);
                frame.remove(changeSyncFolderButton);
                frame.remove(ipTab);
                frame.remove(yourIp);
                frame.remove(swarmInfo);
                frame.remove(filesTab);
                frame.revalidate();
                frame.repaint();
                connectScreen = new ConnectScreen(frame);
            }
        });


        quitButton = new JButton();

        // quitButton Settings:
        quitButton.setText("Quit");
        quitButton.setFont(new Font("Radio Canada", Font.BOLD, 15));

        quitButton.setForeground(new Color(0x000000));
        quitButton.setBackground(new Color(0xB1B6A6));

        quitButton.setBounds(650, 410, 100, 30);
        quitButton.setFocusable(false);

        quitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // commandExecutor.ExecuteOperation(new ExitApp());
                System.exit(1);
            }
        });

        frame.add(yourIp);
        frame.add(swarmInfo);
        frame.add(disconnectButton);
        frame.add(quitButton);
    }
}