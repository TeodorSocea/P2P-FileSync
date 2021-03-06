package GUI;
import Networking.Networking.NetworkManager;
import Networking.Swarm.NetworkSwarm;
import Networking.Swarm.NetworkSwarmManager;
import Networking.Utils.Invitation;
import Resident_Daemon.CommandsPack.CommandExecutor;
import Resident_Daemon.CommandsPack.Commands.CreateSwarm;
import Resident_Daemon.Core.Main;
import Resident_Daemon.Core.Singleton;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import static java.lang.System.exit;
import Resident_Daemon.CommandsPack.Commands.*;
import Resident_Daemon.Core.UserData;

public class ConnectScreen extends JFrame {
    private final GUI_Component frame;
    WorkScreen workScreen;


    // SWARM BUTTONS:
    JButton viewSwarmsButton;

    JPanel swarmList;
    JButton[] swarmIPS;

    JButton joinButton;

    JButton backSwarmButton;


    // viewInvites declaration:
    JButton viewInvitesButton;

    JPanel inviteList;
    JButton[] inviteIPS;

    JButton acceptButton;
    JButton declineButton;

    JButton backInvitesButton;


    // createNewSwarm declaration:
    JButton newSwarmButton;


    // changeSyncFolder:
    JButton changeSyncFolderButton;


    // miscElements declaration:
    JLabel title;
    JButton helpButton;
    JButton quitButton;


    // ceva:
    CommandExecutor commandExecutor;
    UserData userData;

    String acceptIpSend;
    String joinSwarmSend;


    public ConnectScreen(GUI_Component frame) {
        commandExecutor = Singleton.getSingletonObject().getCommandExecutor();
        userData = Singleton.getSingletonObject().getUserData();
        this.frame = frame;

        title = new JLabel();

        // Title settings:
        title.setText("P2P File Sync");
        title.setFont(new Font("Radio Canada", Font.BOLD, 96));

        title.setForeground(new Color(0xA98B93));
        title.setBounds(330, -100, 884, 500);

        frame.add(title);

        Main.ResidentDaemonINIT();

        selectFolder();

        menuElements();
    }


    // Draw elements:
    private void menuElements() {
        viewSwarms();
        viewInvites();
        createNewSwarm();
        changeSyncFolderTab();
        miscElements();

        frame.revalidate();
        frame.repaint();
    }

    private void selectFolder() {
        try {
            Path folderToSyncPath = Singleton.getSingletonObject().getFolderToSyncPath();
        } catch (NullPointerException e) {
            JFileChooser chooser = new JFileChooser();
            chooser.setCurrentDirectory(new java.io.File("."));
            chooser.setDialogTitle("Choose Swarms Folder");
            chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            chooser.setAcceptAllFileFilterUsed(false);
            if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
                String activeFolderPath = chooser.getSelectedFile().getAbsolutePath();
                commandExecutor.ExecuteOperation(new ChooseFolder(activeFolderPath));
            }
            else{
                System.out.println("GUI: No folder path selected!");
                JOptionPane.showMessageDialog(frame,
                        "You have to choose a folder path in order to use this app.",
                        "Error",
                        JOptionPane.PLAIN_MESSAGE);
                selectFolder();
            }
        }
    }


    // View Swarms:
    private void viewSwarms() {
        viewSwarmsButton = new JButton();

        viewSwarmsButton.setText("View Swarms");
        viewSwarmsButton.setFont(new Font("Radio Canada", Font.BOLD, 15));

        viewSwarmsButton.setForeground(new Color(0xFBFCF5));
        viewSwarmsButton.setBackground(new Color(0x3F3D4B));

        viewSwarmsButton.setBounds(540, 300, 200, 30);
        viewSwarmsButton.setFocusable(false);

        viewSwarmsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                commandExecutor.ExecuteOperation(new PrintSwarms());
                if (userData.getMySwarms().isEmpty()) {
                    System.out.println("GUI: No swarms found.");
                    JOptionPane.showMessageDialog(frame,
                            "You have no swarms.",
                            "Error",
                            JOptionPane.PLAIN_MESSAGE);
                }
                else {
                    frame.remove(newSwarmButton);
                    frame.remove(viewSwarmsButton);
                    frame.remove(viewInvitesButton);
                    frame.remove(changeSyncFolderButton);
                    removeMiscElements();


                    swarmList = new JPanel();
                    JPanel pSwm = new JPanel(new GridBagLayout());

                    swarmList.add(new JScrollPane(pSwm, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER));
                    swarmList.setLayout(new BoxLayout(swarmList, BoxLayout.PAGE_AXIS));

                    swarmList.setBounds(566, 250, 150, 250);
                    swarmList.setMaximumSize(new Dimension(100, 200));

                    swarmList.setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, new Color(0x363946)));
                    swarmList.setAutoscrolls(true);

                    pSwm.setFont(new Font("Radio Canada", Font.ITALIC, 25));
                    pSwm.setForeground(new Color(0xFBFCF5));
                    pSwm.setBackground(new Color(0x363946));

                    GridBagConstraints gbcSwarm = new GridBagConstraints();
                    gbcSwarm.insets = new Insets(5, 5, 5, 180);

                    swarmIPS = new JButton[10];

                    int ij = 0;
                    for (var swarmEntry : userData.getMySwarms().entrySet()) {
                        String temp = swarmEntry.getValue().getSwarmName();

                        gbcSwarm.gridy = ij;
                        gbcSwarm.gridx = 0;

                        swarmIPS[ij] = new JButton(temp);
                        pSwm.add(swarmIPS[ij], gbcSwarm);

                        int i = ij;

                        swarmIPS[ij].addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                joinSwarmSend = swarmIPS[i].getText();
                                System.out.println("GUI: Swarm selected: " + swarmIPS[i].getText());

                                if (joinButton != null)
                                    frame.remove(joinButton);

                                NetworkSwarm selectedSwarm = new NetworkSwarm(swarmEntry.getValue().getSwarmID(), swarmEntry.getValue().getSelfID(), swarmEntry.getValue().getSwarmName());

                                joinButton = new JButton();
                                joinButton.setText("Join");
                                joinButton.setFont(new Font("Radio Canada", Font.BOLD, 15));

                                joinButton.setForeground(new Color(0xFBFCF5));
                                joinButton.setBackground(new Color(0x3F3D4B));

                                joinButton.setBounds(590, 585, 85, 30);;
                                joinButton.setFocusable(false);

                                joinButton.addActionListener(new ActionListener() {
                                    @Override
                                    public void actionPerformed(ActionEvent e) {
                                        System.out.print("GUI: Joining Swarm: " + selectedSwarm.getSwarmName());

                                        frame.remove(title);
                                        frame.remove(swarmList);
                                        frame.remove(joinButton);
                                        frame.remove(backSwarmButton);

                                        frame.revalidate();
                                        frame.repaint();
                                        workScreen = new WorkScreen(frame, selectedSwarm);
                                    }
                                });

                                frame.add(joinButton);
                                frame.revalidate();
                                frame.repaint();
                            }
                        });

                        gbcSwarm.gridx = 1;
                        ij++;
                    }

                    backSwarmButton = new JButton();

                    backSwarmButton.setText("BACK");
                    backSwarmButton.setFont(new Font("Radio Canada", Font.BOLD, 15));

                    backSwarmButton.setForeground(new Color(0xFBFCF5));
                    backSwarmButton.setBackground(new Color(0x3F3D4B));

                    backSwarmButton.setBounds(590, 635, 85, 30);
                    backSwarmButton.setFocusable(false);

                    backSwarmButton.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            System.out.println("GUI: Back to connect screen.");

                            frame.remove(backSwarmButton);
                            frame.remove(swarmList);
                            if (joinButton != null)
                                frame.remove(joinButton);

                            menuElements();
                        }
                    });

                    frame.add(swarmList);
                    frame.add(backSwarmButton);

                    frame.revalidate();
                    frame.repaint();
                }
            }
        });

        frame.add(viewSwarmsButton);

        frame.revalidate();
        frame.repaint();
    }


    // View Invites:
    private void viewInvites() {
        viewInvitesButton = new JButton();

        // viewInvitesButton settings:
        viewInvitesButton.setText("View Invites");
        viewInvitesButton.setFont(new Font("Radio Canada", Font.BOLD, 15));

        viewInvitesButton.setForeground(new Color(0xFBFCF5));
        viewInvitesButton.setBackground(new Color(0x3F3D4B));

        viewInvitesButton.setBounds(540, 350, 200, 30);
        viewInvitesButton.setFocusable(false);

        // viewInvitesButton function:
        viewInvitesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("GUI: View invitations.");

                commandExecutor.ExecuteOperation(new PrintInvitations());

                if (userData.getUserInvitations().isEmpty()) {
                    System.out.println("GUI: No invites found.");
                    JOptionPane.showMessageDialog(frame,
                            "You have no invitations.",
                            "Error",
                            JOptionPane.PLAIN_MESSAGE);
                }
                else {
                    frame.remove(newSwarmButton);
                    frame.remove(viewSwarmsButton);
                    frame.remove(viewInvitesButton);
                    frame.remove(changeSyncFolderButton);
                    removeMiscElements();

                    inviteList = new JPanel();
                    JPanel pInv = new JPanel(new GridBagLayout());

                    inviteList.setLayout(new BoxLayout(inviteList, BoxLayout.PAGE_AXIS));
                    inviteList.setBounds(566, 250, 150, 250);

                    inviteList.setMaximumSize(new Dimension(100, 200));
                    inviteList.setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, new Color(0x363946)));

                    inviteList.add(new JScrollPane(pInv, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER));
                    inviteList.setAutoscrolls(true);

                    pInv.setFont(new Font("Radio Canada", Font.ITALIC, 25));
                    pInv.setForeground(new Color(0xFBFCF5));
                    pInv.setBackground(new Color(0x363946));

                    GridBagConstraints gbcInvite = new GridBagConstraints();
                    gbcInvite.insets = new Insets(5, 5, 5, 180);

                    inviteIPS = new JButton[10];



                    int ii = 0;
                    for (Invitation invitation : userData.getUserInvitations()) {
                        gbcInvite.gridy = ii;
                        gbcInvite.gridx = 0;

                        inviteIPS[ii] = new JButton("From: " + invitation.getSenderID());
                        pInv.add(inviteIPS[ii], gbcInvite);

                        int i = ii;

                        inviteIPS[ii].addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                acceptIpSend = inviteIPS[i].getText();

                                if (acceptButton != null) {
                                    frame.remove(acceptButton);
                                    frame.remove(declineButton);
                                }

                                acceptButton = new JButton();

                                acceptButton.setText("Accept");
                                acceptButton.setFont(new Font("Radio Canada", Font.BOLD, 15));

                                acceptButton.setForeground(new Color(0xFBFCF5));
                                acceptButton.setBackground(new Color(0x3F3D4B));

                                acceptButton.setBounds(540, 585, 85, 30);
                                acceptButton.setFocusable(false);

                                acceptButton.addActionListener(new ActionListener() {
                                    @Override
                                    public void actionPerformed(ActionEvent e) {
                                        System.out.print("GUI: Accepting invitation from: " + acceptIpSend);
                                        commandExecutor.ExecuteOperation(new RespondToInvitation(i, true));

                                        frame.remove(inviteList);
                                        frame.remove(acceptButton);
                                        frame.remove(declineButton);
                                        frame.remove(backInvitesButton);

                                        menuElements();
                                    }
                                });


                                declineButton = new JButton();

                                declineButton.setText("Decline");
                                declineButton.setFont(new Font("Radio Canada", Font.BOLD, 15));

                                declineButton.setForeground(new Color(0xFBFCF5));
                                declineButton.setBackground(new Color(0x3F3D4B));

                                declineButton.setBounds(655, 585, 85, 30);
                                declineButton.setFocusable(false);

                                declineButton.addActionListener(new ActionListener() {
                                    @Override
                                    public void actionPerformed(ActionEvent e) {
                                        System.out.print("GUI: Declining invitation from: " + acceptIpSend);
                                        commandExecutor.ExecuteOperation(new RespondToInvitation(i, false));

                                        frame.remove(inviteList);
                                        frame.remove(acceptButton);
                                        frame.remove(declineButton);
                                        frame.remove(backInvitesButton);

                                        menuElements();
                                    }
                                });

                                frame.add(acceptButton);
                                frame.add(declineButton);

                                frame.revalidate();
                                frame.repaint();
                            }
                        });

                        gbcInvite.gridx = 1;
                        ii++;
                    }

                    backInvitesButton = new JButton();

                    backInvitesButton.setText("BACK");
                    backInvitesButton.setFont(new Font("Radio Canada", Font.BOLD, 15));

                    backInvitesButton.setForeground(new Color(0xFBFCF5));
                    backInvitesButton.setBackground(new Color(0x3F3D4B));

                    backInvitesButton.setBounds(590, 635, 85, 30);
                    backInvitesButton.setFocusable(false);

                    backInvitesButton.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            System.out.println("// BACK SWARM IPS BUTTON PRESSED");


                            frame.remove(inviteList);

                            if (acceptButton != null) {
                                frame.remove(acceptButton);
                                frame.remove(declineButton);
                                frame.remove(backInvitesButton);
                            }

                            menuElements();
                        }
                    });

                    frame.add(inviteList);
                    frame.add(backInvitesButton);

                    frame.revalidate();
                    frame.repaint();
                }
            }
        });

        frame.add(viewInvitesButton);

        frame.revalidate();
        frame.repaint();
    }


    // Create New Swarm:
    private void createNewSwarm() {
        newSwarmButton = new JButton();

        // newSwarmButton settings:
        newSwarmButton.setText("Create New Swarm");
        newSwarmButton.setFont(new Font("Radio Canada", Font.BOLD, 15));

        newSwarmButton.setForeground(new Color(0xFBFCF5));
        newSwarmButton.setBackground(new Color(0x3F3D4B));

        newSwarmButton.setBounds(540, 450, 200, 30);
        newSwarmButton.setFocusable(false);

        // newSwarmButton function:
        newSwarmButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("GUI: Creating new swarm. ");
                String chosenName = null;
                chosenName = JOptionPane.showInputDialog("Swarm ID:");
                System.out.println("GUI: Swarm created with name: " + chosenName);

                CreateSwarm createdSwarm = new CreateSwarm(chosenName);
                commandExecutor.ExecuteOperation(createdSwarm);
                NetworkSwarm latestSwarmCreated = userData.getLastCreatedSwarm();

                frame.remove(newSwarmButton);
                frame.remove(viewInvitesButton);
                frame.remove(viewSwarmsButton);
                frame.remove(changeSyncFolderButton);

                frame.remove(helpButton);
                frame.remove(title);
                frame.remove(quitButton);

                frame.revalidate();
                frame.repaint();

                workScreen = new WorkScreen(frame, latestSwarmCreated);
            }
        });

        frame.add(newSwarmButton);
    }

    // changeSyncFolder:
    private void changeSyncFolderTab() {
        changeSyncFolderButton = new JButton();

        changeSyncFolderButton.setText("Change Sync Folder");
        changeSyncFolderButton.setFont(new Font("Radio Canada", Font.BOLD, 15));

        changeSyncFolderButton.setForeground(new Color(0xFBFCF5));
        changeSyncFolderButton.setBackground(new Color(0x3F3D4B));

        changeSyncFolderButton.setBounds(540, 500, 200, 30);
        changeSyncFolderButton.setFocusable(false);

        changeSyncFolderButton.setAlignmentX(-1);
        changeSyncFolderButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser chooser = new JFileChooser();
                chooser.setCurrentDirectory(new java.io.File("."));
                chooser.setDialogTitle("Choose Swarms Folder");
                chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                chooser.setAcceptAllFileFilterUsed(false);
                if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
                    String activeFolderPath = chooser.getSelectedFile().getAbsolutePath();
                    commandExecutor.ExecuteOperation(new ChooseFolder(activeFolderPath));
                }
                else{
                    System.out.println("GUI: No folder path selected!");
                    JOptionPane.showMessageDialog(frame,
                            "You have to choose a folder path in order to use this app.",
                            "Error",
                            JOptionPane.PLAIN_MESSAGE);
                    selectFolder();
                }
            }
        });

        frame.add(changeSyncFolderButton);
    }


    // Title, Help, Quit:
    private void miscElements() {
        helpButton = new JButton();

        // helpButton settings:
        helpButton.setText("Help");
        helpButton.setFont(new Font("Radio Canada", Font.BOLD, 15));

        helpButton.setForeground(new Color(0xFBFCF5));
        helpButton.setBackground(new Color(0x3F3D4B));

        helpButton.setBounds(540, 635, 85, 30);
        helpButton.setFocusable(false);

        // helpButton function:
        helpButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("GUI: Displaying help.");
                JOptionPane.showMessageDialog(frame,
                        "Accept an invite, join or create an existing swarm\n"
                                + "in order to access the features of the app.\n"
                                + "Once in a swarm, you can invite users either manually by\n"
                                + "inserting their IP, or refreshing the nearby IPs.\n"
                                + "\nBy clicking a file you can see its timestamps, and\n"
                                + "clicking one will show you the changes.\n"
                                + "You can either or rollback changes\n"
                                + "based on pressed timestamp.",
                        "Help",
                        JOptionPane.PLAIN_MESSAGE);
            }
        });


        quitButton = new JButton();

        // quitButton settings:
        quitButton.setText("Quit");
        quitButton.setFont(new Font("Radio Canada", Font.BOLD, 15));

        quitButton.setForeground(new Color(0xFBFCF5));
        quitButton.setBackground(new Color(0x3F3D4B));

        quitButton.setBounds(655, 635, 85, 30);
        quitButton.setFocusable(false);

        // quitButton function:
        quitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("GUI: Exiting.");
                commandExecutor.ExecuteOperation(new ExitApp());
                exit(1);
            }
        });


        // Add:
        frame.add(helpButton);
        frame.add(quitButton);
    }

    private void removeMiscElements() {
        frame.remove(helpButton);
        frame.remove(quitButton);
    }
}