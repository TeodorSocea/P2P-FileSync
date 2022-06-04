package GUI;
import Networking.Networking.NetworkManager;
import Networking.Swarm.NetworkSwarm;
import Networking.Utils.Invitation;
import Resident_Daemon.CommandsPack.CommandExecutor;
import Resident_Daemon.CommandsPack.Commands.CreateSwarm;
import Resident_Daemon.Core.Singleton;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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

        title.setForeground(new Color(0xB1B6A6));
        title.setBounds(330, -100, 884, 500);

        frame.add(title);

        menuElements();
    }


    // Draw elements:
    private void menuElements() {
        viewSwarms();
        viewInvites();
        createNewSwarm();
        miscElements();

        frame.revalidate();
        frame.repaint();
    }


    // View Swarms:
    private void viewSwarms() {
        viewSwarmsButton = new JButton();

        viewSwarmsButton.setText("VIEW SWARMS");
        viewSwarmsButton.setFont(new Font("Radio Canada", Font.BOLD, 15));

        viewSwarmsButton.setForeground(new Color(0x000000));
        viewSwarmsButton.setBackground(new Color(0xB1B6A6));

        viewSwarmsButton.setBounds(540, 300, 200, 30);
        viewSwarmsButton.setFocusable(false);

        viewSwarmsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                commandExecutor.ExecuteOperation(new PrintSwarms());
                if (userData.getMySwarms().size() == 0) {
                    System.out.println("GUI: No swarms found.");
                    JOptionPane.showMessageDialog(frame,
                            "You have no swarms.",
                            "Error",
                            JOptionPane.PLAIN_MESSAGE);
                }
                else {
                    frame.remove(newSwarmButton);
                    frame.remove(viewInvitesButton);
                    removeMiscElements();
                    frame.remove(viewSwarmsButton);


                    swarmList = new JPanel();
                    JPanel pSwm = new JPanel(new GridBagLayout());

                    swarmList.add(new JScrollPane(pSwm, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER));
                    swarmList.setLayout(new BoxLayout(swarmList, BoxLayout.PAGE_AXIS));

                    swarmList.setBounds(566, 250, 150, 250);
                    swarmList.setMaximumSize(new Dimension(100, 200));

                    swarmList.setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, new Color(0x363946)));
                    swarmList.setAutoscrolls(true);

                    pSwm.setFont(new Font("Radio Canada", Font.ITALIC, 25));
                    pSwm.setForeground(new Color(0x000000));
                    pSwm.setBackground(new Color(0x363946));

                    GridBagConstraints gbcSwarm = new GridBagConstraints();
                    gbcSwarm.insets = new Insets(5, 5, 5, 180);

                    swarmIPS = new JButton[10];

                    int ij = 0;
                    for (var swarmEntry : userData.getMySwarms().entrySet()) {
                        int temp = swarmEntry.getValue().getSwarmID();

                        gbcSwarm.gridy = ij;
                        gbcSwarm.gridx = 0;

                        swarmIPS[ij] = new JButton("Swarm " + temp);
                        pSwm.add(swarmIPS[ij], gbcSwarm);

                        swarmIPS[ij].addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                if (swarmIPS != null) {
                                    for (int i = 0; i < swarmIPS.length; i++) {
                                        if (e.getSource() == swarmIPS[i]) {
                                            joinSwarmSend = swarmIPS[i].getText();
                                            System.out.println("GUI: Swarm selected: " + swarmIPS[i].getText());

                                            frame.remove(joinButton);

                                            joinButton = new JButton();
                                            joinButton.setText("Join");
                                            joinButton.setFont(new Font("Radio Canada", Font.BOLD, 15));

                                            joinButton.setForeground(new Color(0x000000));
                                            joinButton.setBackground(new Color(0xB1B6A6));

                                            joinButton.setBounds(590, 585, 85, 30);;
                                            joinButton.setFocusable(false);

                                            joinButton.addActionListener(new ActionListener() {
                                                @Override
                                                public void actionPerformed(ActionEvent e) {
                                                    System.out.print("GUI: Joining Swarm: " + joinSwarmSend);

                                                    int swarm = Integer.parseInt(joinSwarmSend.replaceAll("[^0-9]", ""));

                                                    workScreen = new WorkScreen(frame, swarm);
                                                    frame.removeAll();
                                                    frame.repaint();
                                                }
                                            });

                                        }
                                    }
                                }
                            }
                        });

                        gbcSwarm.gridx = 1;
                        ij++;
                    }

                    backSwarmButton = new JButton();

                    backSwarmButton.setText("BACK");
                    backSwarmButton.setFont(new Font("Radio Canada", Font.BOLD, 15));

                    backSwarmButton.setForeground(new Color(0x000000));
                    backSwarmButton.setBackground(new Color(0xB1B6A6));

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
        viewInvitesButton.setText("VIEW INVITES");
        viewInvitesButton.setFont(new Font("Radio Canada", Font.BOLD, 15));

        viewInvitesButton.setForeground(new Color(0x000000));
        viewInvitesButton.setBackground(new Color(0xB1B6A6));

        viewInvitesButton.setBounds(540, 350, 200, 30);
        viewInvitesButton.setFocusable(false);

        // viewInvitesButton function:
        viewInvitesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("GUI: View invitations.");

                if (userData.getUserInvitations() == null) {
                    System.out.println("GUI: No invites found.");
                    JOptionPane.showMessageDialog(frame,
                            "You have no invitations.",
                            "Error",
                            JOptionPane.PLAIN_MESSAGE);
                }
                else {
                    frame.remove(newSwarmButton);
                    removeMiscElements();
                    frame.remove(viewSwarmsButton);
                    frame.remove(viewInvitesButton);

                    inviteList = new JPanel();
                    JPanel pInv = new JPanel(new GridBagLayout());

                    inviteList.setLayout(new BoxLayout(inviteList, BoxLayout.PAGE_AXIS));
                    inviteList.setBounds(566, 250, 150, 250);

                    inviteList.setMaximumSize(new Dimension(100, 200));
                    inviteList.setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, new Color(0x363946)));

                    inviteList.add(new JScrollPane(pInv, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER));
                    inviteList.setAutoscrolls(true);

                    pInv.setFont(new Font("Radio Canada", Font.ITALIC, 25));
                    pInv.setForeground(new Color(0x000000));
                    pInv.setBackground(new Color(0x363946));

                    GridBagConstraints gbcInvite = new GridBagConstraints();
                    gbcInvite.insets = new Insets(5, 5, 5, 180);

                    inviteIPS = new JButton[10];

                    int ii = 0;
                    for (Invitation invitation : userData.getUserInvitations()) {
                        // for (int ii = 0; ii < 10; ii++) {
                        gbcInvite.gridy = ii;
                        gbcInvite.gridx = 0;

                        inviteIPS[ii] = new JButton("Button " + invitation.getSenderID());
                        pInv.add(inviteIPS[ii], gbcInvite);

                        inviteIPS[ii].addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                if (inviteIPS != null) {
                                    for (int j = 0; j < inviteIPS.length; j++) {
                                        if (e.getSource() == inviteIPS[j]) {
                                            acceptIpSend = inviteIPS[j].getText();

                                            frame.remove(acceptButton);
                                            frame.remove(declineButton);

                                            acceptButton = new JButton();

                                            acceptButton.setText("Accept");
                                            acceptButton.setFont(new Font("Radio Canada", Font.BOLD, 15));

                                            acceptButton.setForeground(new Color(0x000000));
                                            acceptButton.setBackground(new Color(0xB1B6A6));

                                            acceptButton.setBounds(540, 585, 85, 30);
                                            acceptButton.setFocusable(false);

                                            acceptButton.addActionListener(new ActionListener() {
                                                @Override
                                                public void actionPerformed(ActionEvent e) {
                                                    System.out.print("GUI: Accepting invitation from: " + acceptIpSend);
                                                    // commandExecutor.ExecuteOperation(RespondToInvitation(int index(a cata invitatie e), bool response));
                                                }
                                            });


                                            declineButton = new JButton();

                                            declineButton.setText("Decline");
                                            declineButton.setFont(new Font("Radio Canada", Font.BOLD, 15));

                                            declineButton.setForeground(new Color(0x000000));
                                            declineButton.setBackground(new Color(0xB1B6A6));

                                            declineButton.setBounds(655, 585, 85, 30);
                                            declineButton.setFocusable(false);

                                            declineButton.addActionListener(new ActionListener() {
                                                @Override
                                                public void actionPerformed(ActionEvent e) {
                                                    System.out.print("GUI: Declining invitation from: " + acceptIpSend);
                                                    // commandExecutor.ExecuteOperation(RespondToInvitation(int index(a cata invitatie e), bool response));
                                                }
                                            });


                                            frame.add(acceptButton);
                                            frame.add(declineButton);

                                            frame.revalidate();
                                            frame.repaint();
                                        }
                                    }
                                }
                            }
                        });

                        gbcInvite.gridx = 1;
                        // ii++;
                    }

                    backInvitesButton = new JButton();

                    backInvitesButton.setText("BACK");
                    backInvitesButton.setFont(new Font("Radio Canada", Font.BOLD, 15));

                    backInvitesButton.setForeground(new Color(0x000000));
                    backInvitesButton.setBackground(new Color(0xB1B6A6));

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
        newSwarmButton.setText("CREATE NEW SWARM");
        newSwarmButton.setFont(new Font("Radio Canada", Font.BOLD, 15));

        newSwarmButton.setForeground(new Color(0x000000));
        newSwarmButton.setBackground(new Color(0xB1B6A6));

        newSwarmButton.setBounds(540, 450, 200, 30);
        newSwarmButton.setFocusable(false);

        // newSwarmButton function:
        newSwarmButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("GUI: Creating new swarm. ");
                String chosenID = null;
                chosenID = JOptionPane.showInputDialog("Swarm ID:");
                System.out.println("GUI: Swarm created with ID " + chosenID);
                // commandExecutor.ExecuteOperation(new CreateSwarm(string invited));
                int swarm = userData.getLastCreatedSwarm();

                frame.remove(newSwarmButton);
                frame.remove(viewInvitesButton);
                frame.remove(viewSwarmsButton);

                frame.remove(helpButton);
                frame.remove(title);
                frame.remove(quitButton);

                frame.revalidate();
                frame.repaint();

                workScreen = new WorkScreen(frame, swarm);
            }
        });

        frame.add(newSwarmButton);
    }


    // Title, Help, Quit:
    private void miscElements() {
        helpButton = new JButton();

        // helpButton settings:
        helpButton.setText("HELP");
        helpButton.setFont(new Font("Radio Canada", Font.BOLD, 15));

        helpButton.setForeground(new Color(0x000000));
        helpButton.setBackground(new Color(0xB1B6A6));

        helpButton.setBounds(540, 635, 85, 30);
        helpButton.setFocusable(false);

        // helpButton function:
        helpButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("GUI: Displaying help.");
                JOptionPane.showMessageDialog(frame,
                        "Move the chess pieces on the board by clicking\n"
                                + "and dragging. The game will watch out for illegal\n"
                                + "moves. You can win either by your opponent running\n"
                                + "out of time or by checkmating your opponent.\n"
                                + "\nGood luck, hope you enjoy the game!",
                        "How to play",
                        JOptionPane.PLAIN_MESSAGE);
            }
        });


        quitButton = new JButton();

        // quitButton settings:
        quitButton.setText("QUIT");
        quitButton.setFont(new Font("Radio Canada", Font.BOLD, 15));

        quitButton.setForeground(new Color(0x000000));
        quitButton.setBackground(new Color(0xB1B6A6));

        quitButton.setBounds(655, 635, 85, 30);
        quitButton.setFocusable(false);

        // quitButton function:
        quitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("GUI: Exiting.");
                // commandExecutor.ExecuteOperation(new ExitApp());
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