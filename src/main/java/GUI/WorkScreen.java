package GUI;
import GUI.GUI_Component;
import Networking.Core.NetworkingComponent;
import Networking.Peer.Peer;
import Networking.Swarm.NetworkSwarm;
import Networking.Swarm.NetworkSwarmManager;
import Resident_Daemon.CommandsPack.CommandExecutor;
import Resident_Daemon.CommandsPack.Commands.*;
import Resident_Daemon.Core.Main;
import Resident_Daemon.Core.Singleton;
import Resident_Daemon.Core.UserData;
import org.w3c.dom.ls.LSOutput;

import java.sql.SQLOutput;
import java.util.*;
import javax.sound.midi.Soundbank;
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

    JButton[] filesList;
    JPanel filesTab;
    JPanel timeStampsTab;
    JButton[] timeStampsList;
    JButton versionsRefreshButton;
    String savedFile;
    String savedTimeStamp;

    JPanel ipTab;
    JButton[] ipList;
    JButton inviteToSwarmButton;
    JButton ipsRefreshButton;
    JButton ipManualInviteButton;
    String selectedIP = null;



    JButton rollBackButton;
    JButton syncButton;


    JLabel swarmInfo;
    JLabel yourIp;
    JButton disconnectButton;
    JButton quitButton;


    // DEPRECATED:
    String activeFolderPath=null;
    JButton changeSyncFolderButton;
    // --


    CommandExecutor commandExecutor;
    NetworkSwarm networkSwarm;


    public WorkScreen(GUI_Component frame, NetworkSwarm currentSwarm){
        this.frame = frame;
        commandExecutor = Singleton.getSingletonObject().getCommandExecutor();
        networkSwarm = currentSwarm;

        fileListTab();

        versionsRefreshButton();


        inviteToSwarmTab();

        inviteToSwarmButton();

        ipsRefreshButton();

        ipManualInviteButton();


        syncButton();

        rollBackButton();


        miscElements();

        frame.revalidate();
        frame.repaint();
    }


    // fileVersions:
    private void fileListTab() {
        filesTab = new JPanel();
        JPanel m = new JPanel(new GridBagLayout());
        filesTab.add(new JScrollPane(m, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER));
        GridBagConstraints gbcc = new GridBagConstraints();
        filesTab.setLayout(new BoxLayout(filesTab, BoxLayout.PAGE_AXIS));
        filesTab.setBounds(310,118,200,425);
        filesTab.setMaximumSize(new Dimension(100, 200));
        m.setFont(new Font("Radio Canada", Font.ITALIC, 25));
        m.setForeground(new Color(0xFBFCF5));
        m.setBackground(new Color(0x3F3D4B));
        filesTab.setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, Color.black));
        filesTab.setAutoscrolls(true);
        filesList = new JButton[10];
        gbcc.insets = new Insets(5,5,5,180);
        int ji = 0;
        try {
            commandExecutor.ExecuteOperation(new GetFilesFromVersionFile(networkSwarm.getSwarmID()));

            for (var fisier : Singleton.getSingletonObject().getUserData().getVersionFileFiles()) {
                gbcc.gridy = ji;
                gbcc.gridx = 0;
                filesList[ji] = new JButton("" + fisier);
                filesList[ji].setBackground(new Color(0x727482));
                filesList[ji].setForeground(new Color(0xFBFCF5));
                m.add(filesList[ji], gbcc);

                int i = ji;

                filesList[ji].addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        System.out.println("GUI: File pressed: " + filesList[i].getText());

                        savedFile = filesList[i].getText();

                        if (timeStampsTab != null){
                            frame.remove(timeStampsTab);
                            frame.revalidate();
                            frame.repaint();
                        }

                        commandExecutor.ExecuteOperation(new GetTimestampsFromFileVersionFile(filesList[i].getText()));
                        timeStampsTab(filesList[i].getText());
                        frame.revalidate();
                        frame.repaint();
                    }
                });

                gbcc.gridx = 1;
                ji++;
            }
        }
        catch (NullPointerException n) {
            System.out.println(n);
        }

        frame.add(filesTab);

    }

    private void timeStampsTab(String fileName) {
        timeStampsTab = new JPanel();
        JPanel mm = new JPanel(new GridBagLayout());
        timeStampsTab.add(new JScrollPane(mm, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER));
        GridBagConstraints gbccc = new GridBagConstraints();
        timeStampsTab.setLayout(new BoxLayout(timeStampsTab, BoxLayout.PAGE_AXIS));
        timeStampsTab.setBounds(130,118,160,425);
        timeStampsTab.setMaximumSize(new Dimension(100, 200));
        mm.setFont(new Font("Radio Canada", Font.ITALIC, 25));
        mm.setForeground(new Color(0xFBFCF5));
        mm.setBackground(new Color(0x3F3D4B));
        timeStampsTab.setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, Color.black));
        timeStampsTab.setAutoscrolls(true);
        timeStampsList = new JButton[10];
        gbccc.insets = new Insets(5,5,5,180);
        int ji = 0;

        commandExecutor.ExecuteOperation(new GetFilesFromVersionFile(networkSwarm.getSwarmID()));
        for (var tms : Singleton.getSingletonObject().getUserData().getTimestampVersionFileFiles()) {
            gbccc.gridy = ji;
            gbccc.gridx = 0;
            timeStampsList[ji] = new JButton("" + tms);
            timeStampsList[ji].setBackground(new Color(0x727482));
            timeStampsList[ji].setForeground(new Color(0xFBFCF5));
            timeStampsList[ji].setSize(170, 25);
            mm.add(timeStampsList[ji], gbccc);

            int i = ji;

            timeStampsList[ji].addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    System.out.println("GUI: Time Stamp pressed: " + timeStampsList[i].getText());

                    savedTimeStamp = timeStampsList[i].getText();

                    commandExecutor.ExecuteOperation(new GetChangesFromTimestampVersionFile(fileName, timeStampsList[i].getText()));
                    JOptionPane.showMessageDialog(frame,
                            Singleton.getSingletonObject().getUserData().getChangesFileVersionFile(),
                            "Changes",
                            JOptionPane.PLAIN_MESSAGE);
                    frame.revalidate();
                    frame.repaint();
                }
            });

            gbccc.gridx = 1;
            ji++;
        }

        frame.add(timeStampsTab);
    }

    private void versionsRefreshButton() {
        versionsRefreshButton = new JButton("Refresh Versions");
        versionsRefreshButton.setFont(new Font("Radio Canada", Font.BOLD, 15));

        versionsRefreshButton.setBounds(310,543,200,30);
        versionsRefreshButton.setFocusable(false);

        versionsRefreshButton.setForeground(new Color(0xFBFCF5));
        versionsRefreshButton.setBackground(new Color(0x3F3D4B));

        versionsRefreshButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("GUI: Refreshing versions.");
                frame.remove(filesTab);
                if (timeStampsTab != null) {
                    frame.remove(timeStampsTab);
                }
                fileListTab();
                frame.revalidate();
                frame.repaint();

            }
        });

        frame.add(versionsRefreshButton);

    }


    // inviteToSwarmTab:
    private void inviteToSwarmTab() {
        if(ipTab != null){
            frame.remove(ipTab);
        }
        ipTab = new JPanel();
        JPanel p = new JPanel(new GridBagLayout());

        ipTab.add(new JScrollPane(p, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER));
        ipTab.setLayout(new BoxLayout(ipTab, BoxLayout.PAGE_AXIS));

        ipTab.setBounds(770,118,160,425);
        ipTab.setMaximumSize(new Dimension(100, 200));

        p.setFont(new Font("Radio Canada", Font.ITALIC, 25));
        p.setForeground(new Color(0xFBFCF5));
        p.setBackground(new Color(0x3F3D4B));

        ipTab.setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, Color.black));
        ipTab.setAutoscrolls(true);

        ipList = new JButton[10];

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5,5,5,180);

        int ji = 0;
        commandExecutor.ExecuteOperation(new PrintIPs());

        for (var list : Singleton.getSingletonObject().getUserData().getNearbyIPs()) {
            gbc.gridy = ji;
            gbc.gridx = 0;
            ipList[ji] = new JButton("" + list);
            System.out.println(ipList[ji].getText());
            ipList[ji].setBackground(new Color(0x727482));
            ipList[ji].setForeground(new Color(0xFBFCF5));
            p.add(ipList[ji], gbc);
            int i = ji;
            ipList[ji].addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    selectedIP = ipList[i].getText();
                    System.out.println("GUI: Selected invite for " + selectedIP + ". Awaiting confirmation.");
                    inviteToSwarmButton.setText("Invite?: " + selectedIP);
                    frame.revalidate();
                    frame.repaint();
                }
            });
            gbc.gridx = 1;
            ji++;
        }

        frame.add(ipTab);
        frame.revalidate();
        frame.repaint();

    }

    private void inviteToSwarmButton() {
        inviteToSwarmButton = new JButton("Select IP to invite");
        inviteToSwarmButton.setFont(new Font("Radio Canada", Font.BOLD, 15));

        inviteToSwarmButton.setBounds(530,370,220,30);
        inviteToSwarmButton.setFocusable(false);

        inviteToSwarmButton.setForeground(new Color(0xFBFCF5));
        inviteToSwarmButton.setBackground(new Color(0x3F3D4B));

        inviteToSwarmButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (selectedIP == null) {
                    System.out.println("GUI: No IP to invite.");
                    inviteToSwarmButton.setText("Nothing selected..");
                }
                else {
                    System.out.println("GUI: Inviting IP: " + selectedIP);
                    commandExecutor.ExecuteOperation(new InviteToSwarm(selectedIP, networkSwarm.getSwarmID()));
                    inviteToSwarmButton.setText("Invited. Select another...");
                    selectedIP = null;
                }
            }
        });

        inviteToSwarmButton.setAlignmentX(-1);

        frame.add(inviteToSwarmButton);
    }

    private void ipsRefreshButton() {
        ipsRefreshButton = new JButton("Refresh IPs");
        ipsRefreshButton.setFont(new Font("Radio Canada", Font.BOLD, 15));

        ipsRefreshButton.setBounds(770,543,160,30);
        ipsRefreshButton.setFocusable(false);

        ipsRefreshButton.setForeground(new Color(0xFBFCF5));
        ipsRefreshButton.setBackground(new Color(0x3F3D4B));

        ipsRefreshButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("GUI: Refreshing IPs");
                inviteToSwarmTab();
                frame.revalidate();
                frame.repaint();
            }
        });

        frame.add(ipsRefreshButton);
    }

    private void ipManualInviteButton() {
        ipManualInviteButton = new JButton("Manual Invite");
        ipManualInviteButton.setFont(new Font("Radio Canada", Font.BOLD, 15));

        ipManualInviteButton.setBounds(770,573,160,30);
        ipManualInviteButton.setFocusable(false);

        ipManualInviteButton.setForeground(new Color(0xFBFCF5));
        ipManualInviteButton.setBackground(new Color(0x3F3D4B));

        ipManualInviteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("GUI: Manual invite IP.");
                String chosenIP = null;
                chosenIP = JOptionPane.showInputDialog("IP:");
                System.out.println("GUI: Invited IP: " + chosenIP);
                if (chosenIP != null)
                    commandExecutor.ExecuteOperation(new InviteToSwarm(chosenIP, networkSwarm.getSwarmID()));
                else {
                    System.out.println("GUI: No IP selected for manual invite.");
                    JOptionPane.showMessageDialog(frame,
                            "Missing IP.",
                            "Error",
                            JOptionPane.PLAIN_MESSAGE);
                }
            }
        });

        frame.add(ipManualInviteButton);
    }


    // sync / rollbackFunctions:
    private void syncButton() {
        syncButton = new JButton();

        syncButton.setText("Press to Sync");
        syncButton.setFont(new Font("Radio Canada", Font.BOLD, 15));

        syncButton.setForeground(new Color(0xFBFCF5));
        syncButton.setBackground(new Color(0x3F3D4B));

        syncButton.setBounds(530, 290, 220, 30);
        syncButton.setFocusable(false);

        syncButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.print("GUI: Syncing.");
                commandExecutor.ExecuteOperation(new SyncSwarm(networkSwarm.getSwarmID()));
                syncButton.setText("Synced. Tap again to sync");
                frame.revalidate();
                frame.repaint();
            }
        });

        frame.add(syncButton);
    }

    private void rollBackButton() {
        rollBackButton = new JButton();

        rollBackButton.setText("Press to Rollback");
        rollBackButton.setFont(new Font("Radio Canada", Font.BOLD, 15));

        rollBackButton.setForeground(new Color(0xFBFCF5));
        rollBackButton.setBackground(new Color(0x3F3D4B));

        rollBackButton.setBounds(530, 330, 220, 30);
        rollBackButton.setFocusable(false);

        rollBackButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("GUI: Rolling back. Timestamp: " + savedTimeStamp  + ". File: " + savedFile);
            }
        });

        frame.add(rollBackButton);
    }


    // yourIp, swarmInfo, dcButton, quitButton:
    private void miscElements() {
        yourIp = new JLabel();

        // yourIp settings:
        yourIp.setText("Your Id: " + networkSwarm.getSelfID());
        yourIp.setFont(new Font("Radio Canada", Font.BOLD, 16));

        yourIp.setForeground(new Color(0xFBFCF5));
        yourIp.setBounds(445, 10, 200, 30);


        swarmInfo = new JLabel();

        // swarmInfo settings:
        swarmInfo.setText("Current SwarmID: " + networkSwarm.getSwarmID() + "    Name: "  + networkSwarm.getSwarmName());
        swarmInfo.setFont(new Font("Radio Canada", Font.BOLD, 16));

        swarmInfo.setForeground(new Color(0xFBFCF5));
        swarmInfo.setBounds(735, 10, 500, 30);


        disconnectButton = new JButton();

        // disconnectButton settings:
        disconnectButton.setText("Logout");
        disconnectButton.setFont(new Font("Radio Canada", Font.BOLD, 15));

        disconnectButton.setForeground(new Color(0xFBFCF5));
        disconnectButton.setBackground(new Color(0x3F3D4B));

        disconnectButton.setBounds(530, 410, 100, 30);
        disconnectButton.setFocusable(false);

        // disconnectButton function:
        disconnectButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("GUI: Disconnecting");

                frame.remove(ipTab);
                frame.remove(ipsRefreshButton);
                frame.remove(ipManualInviteButton);
                frame.remove(inviteToSwarmButton);

                frame.remove(filesTab);
                frame.remove(versionsRefreshButton);
                if (timeStampsTab != null)
                    frame.remove(timeStampsTab);

                frame.remove(syncButton);
                frame.remove(rollBackButton);

                frame.remove(disconnectButton);
                frame.remove(quitButton);

                frame.remove(yourIp);
                frame.remove(swarmInfo);

                frame.revalidate();
                frame.repaint();
                connectScreen = new ConnectScreen(frame);
            }
        });


        quitButton = new JButton();

        // quitButton Settings:
        quitButton.setText("Quit");
        quitButton.setFont(new Font("Radio Canada", Font.BOLD, 15));

        quitButton.setForeground(new Color(0xFBFCF5));
        quitButton.setBackground(new Color(0x3F3D4B));

        quitButton.setBounds(650, 410, 100, 30);
        quitButton.setFocusable(false);

        quitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("GUI: Quitting.");
                commandExecutor.ExecuteOperation(new ExitApp());
                System.exit(1);
            }
        });

        frame.add(yourIp);
        frame.add(swarmInfo);
        frame.add(disconnectButton);
        frame.add(quitButton);
    }


    // DEPRECATED
    private void changeSyncFolderTab() {
        changeSyncFolderButton = new JButton();

        changeSyncFolderButton.setText("Change Sync Folder");
        changeSyncFolderButton.setFont(new Font("Radio Canada", Font.BOLD, 15));

        changeSyncFolderButton.setForeground(new Color(0xFBFCF5));
        changeSyncFolderButton.setBackground(new Color(0x3F3D4B));

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
}