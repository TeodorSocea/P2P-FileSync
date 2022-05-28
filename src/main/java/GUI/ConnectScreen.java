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

public class ConnectScreen extends JFrame implements ActionListener {
    private final GUI_Component frame;
    WorkScreen workScreen;

    // SWARM BUTTONS:
    JButton printSwarmsButton;
    JPanel swarmList;
    JButton[] swarmIPS;
    JButton backSwarmButton;
    JButton joinButton;

    // INVITES ELEMENTS:
    JButton viewInvitesButton;
    JPanel inviteList;
    JButton[] inviteIPS;
    JButton backInvitesButton;
    JButton acceptButton;
    JButton declineButton;

    // RANDOM ELEMENTS:
    JButton newSwarmButton;
    JButton helpButton;
    JButton backHelpButton;
    JButton quitButton;
    JLabel helpContents;
    JLabel nullContents;
    JLabel title;

    // CEVA:
    CommandExecutor commandExecutor;
    UserData userData;
    String acceptIpSend;
    String joinSwarmSend;

    @Override
    public void actionPerformed(ActionEvent e){

        // SWARMS - PRINT ACTION:
        if (e.getSource() == printSwarmsButton){
            SwarmGenerator();
            frame.revalidate();
            frame.repaint();
        }

        // SWARMS - BUTTON PRESSED:
        if (swarmIPS != null) {
            for (int i = 0; i < swarmIPS.length; i++) {
                if (e.getSource() == swarmIPS[i]) {
                    joinSwarmSend = swarmIPS[i].getText();
                    System.out.println("// SWARM IP PRESSED: " + swarmIPS[i].getText());
                    frame.remove(joinButton);
                    frame.revalidate();
                    frame.repaint();
                    frame.add(joinButton);
                }
            }
        }

        // SWARMS - JOIN ACTION:
        if (e.getSource() == joinButton) {
            System.out.print(joinSwarmSend);
            int swarm = Integer.parseInt(joinSwarmSend.replaceAll("[^0-9]", ""));
            workScreen = new WorkScreen(frame, swarm);
            frame.remove(joinButton);
            frame.remove(title);
            frame.remove(swarmList);
            frame.remove(backSwarmButton);
            frame.repaint();
        }

        // SWARMS - BACK ACTION:
        if (e.getSource() == backSwarmButton){
            System.out.println("// BACK SWARM IPS BUTTON PRESSED");
            frame.remove(backSwarmButton);
            frame.remove(swarmList);
            frame.remove(backSwarmButton);
            frame.revalidate();
            frame.repaint();
            frame.add(newSwarmButton);
            frame.add(viewInvitesButton);
            frame.add(printSwarmsButton);
            frame.add(helpButton);
            frame.add(quitButton);
            frame.revalidate();
            frame.repaint();
        }

        // INVITES - PRINT ACTION:
        if (e.getSource() == viewInvitesButton) {
            System.out.println("// VIEW INVITATIONS BUTTON PRESSED");
            inviteGenerator();
            frame.revalidate();
            frame.repaint();
        }

        // INVITES - BUTTON PRESSED:
        if (inviteIPS != null) {
            for (int j = 0; j < inviteIPS.length; j++) {
                if (e.getSource() == inviteIPS[j]) {
                    acceptIpSend = inviteIPS[j].getText();
                    frame.remove(acceptButton);
                    frame.remove(declineButton);
                    frame.revalidate();
                    frame.repaint();
                    frame.add(acceptButton);
                    frame.add(declineButton);
                }
            }
        }

        // INVITES - ACCEPT ACTION:
        if (e.getSource() == acceptButton) {
            System.out.print(acceptIpSend);
            // commandExecutor.ExecuteOperation(RespondToInvitation());
        }

        // INVITES - DECLINE ACTION:
        if (e.getSource() == declineButton) {
            System.out.print(acceptIpSend);
            // commandExecutor.ExecuteOperation(RespondToInvitation());
        }

        // INVITES : BACK ACTION:
        if (e.getSource() == backInvitesButton) {
            System.out.println("// BACK SWARM IPS BUTTON PRESSED");
            frame.remove(backInvitesButton);
            frame.remove(inviteList);
            frame.remove(acceptButton);
            frame.remove(declineButton);
            frame.revalidate();
            frame.repaint();
            frame.add(newSwarmButton);
            frame.add(viewInvitesButton);
            frame.add(printSwarmsButton);
            frame.add(helpButton);
            frame.add(quitButton);
            frame.revalidate();
            frame.repaint();
        }

        // NEW SWARM BUTTON PRESS:
        if (e.getSource() == newSwarmButton){
            System.out.println("// CREATE NEW SWARM BUTTON PRESSED");
            commandExecutor.ExecuteOperation(new CreateSwarm());
            int swarm = userData.getLastCreatedSwarm();
            frame.remove(newSwarmButton);
            frame.remove(viewInvitesButton);
            frame.remove(printSwarmsButton);
            frame.remove(helpButton);
            frame.remove(title);
            frame.remove(quitButton);
            frame.revalidate();
            frame.repaint();
            workScreen = new WorkScreen(frame, swarm);
        }

        // HELP PRESS:
        if (e.getSource() == helpButton){
            System.out.println("// HELP BUTTON PRESSED");
            frame.remove(newSwarmButton);
            frame.remove(viewInvitesButton);
            frame.remove(printSwarmsButton);
            frame.remove(helpButton);
            frame.remove(quitButton);
            frame.revalidate();
            frame.repaint();
            frame.add(helpContents);
            frame.add(backHelpButton);
            frame.revalidate();
            frame.repaint();
        }

        // BACK HELP BUTTON:
        if (e.getSource() == backHelpButton){
            System.out.println("// BACK HELP BUTTON PRESSED");
            frame.remove(helpContents);
            frame.remove(backHelpButton);
            frame.revalidate();
            frame.repaint();
            frame.add(newSwarmButton);
            frame.add(viewInvitesButton);
            frame.add(printSwarmsButton);
            frame.add(helpButton);
            frame.add(quitButton);
            frame.revalidate();
            frame.repaint();
        }

        // QUIT PRESS:
        if (e.getSource() == quitButton){
            System.out.println("// QUIT BUTTON PRESSED, BYE!");
            // commandExecutor.ExecuteOperation(new ExitApp());
            exit(1);
        }
    }

    public void SwarmGenerator() {
        commandExecutor.ExecuteOperation(new PrintSwarms());
        if (userData.getMySwarms() == null) {
            System.out.print("I AM HHEREEE");
            frame.add(nullContents);
            frame.revalidate();
            frame.repaint();
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            frame.remove(nullContents);
            frame.revalidate();
            frame.repaint();
        } else {
            frame.remove(newSwarmButton);
            frame.remove(viewInvitesButton);
            frame.remove(printSwarmsButton);
            frame.remove(helpButton);
            frame.remove(quitButton);
            frame.revalidate();
            frame.repaint();
            swarmList = new JPanel();
            JPanel pSwm = new JPanel(new GridBagLayout());
            swarmList.add(new JScrollPane(pSwm, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER));
            GridBagConstraints gbcSwarm = new GridBagConstraints();
            swarmList.setLayout(new BoxLayout(swarmList, BoxLayout.PAGE_AXIS));
            swarmList.setBounds(566, 250, 150, 250);
            swarmList.setMaximumSize(new Dimension(100, 200));
            swarmList.setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, new Color(0x363946)));
            pSwm.setFont(new Font("Radio Canada", Font.ITALIC, 25));
            pSwm.setForeground(new Color(0x000000));
            pSwm.setBackground(new Color(0x363946));
            swarmList.setAutoscrolls(true);
            gbcSwarm.insets = new Insets(5, 5, 5, 180);
            swarmIPS = new JButton[10];
            int ij = 0;

            for (var swarmEntry : userData.getMySwarms().entrySet()) {
                int temp = swarmEntry.getValue().getSwarmID();
                gbcSwarm.gridy = ij;
                gbcSwarm.gridx = 0;
                swarmIPS[ij] = new JButton("Swarm " + temp);
                pSwm.add(swarmIPS[ij], gbcSwarm);
                swarmIPS[ij].addActionListener(this);
                gbcSwarm.gridx = 1;
                ij++;
            }
            frame.add(swarmList);
            frame.add(backSwarmButton);
            frame.revalidate();
            frame.repaint();
        }
    }

    public void inviteGenerator () {
        // commandExecutor.ExecuteOperation(new PrintInvitations());
        if (userData.getUserInvitations() == null) {
            System.out.print("I AM HHEREEE");
            frame.add(nullContents);
            frame.revalidate();
            frame.repaint();
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            frame.remove(nullContents);
            frame.revalidate();
            frame.repaint();
        } else {
            frame.remove(newSwarmButton);
            frame.remove(viewInvitesButton);
            frame.remove(printSwarmsButton);
            frame.remove(helpButton);
            frame.remove(quitButton);
            frame.revalidate();
            frame.repaint();
            inviteList = new JPanel();
            JPanel pInv = new JPanel(new GridBagLayout());
            inviteList.add(new JScrollPane(pInv, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER));
            GridBagConstraints gbcInvite = new GridBagConstraints();
            inviteList.setLayout(new BoxLayout(inviteList, BoxLayout.PAGE_AXIS));
            inviteList.setBounds(566, 250, 150, 250);
            inviteList.setMaximumSize(new Dimension(100, 200));
            inviteList.setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, new Color(0x363946)));
            pInv.setFont(new Font("Radio Canada", Font.ITALIC, 25));
            pInv.setForeground(new Color(0x000000));
            pInv.setBackground(new Color(0x363946));
            inviteList.setAutoscrolls(true);
            gbcInvite.insets = new Insets(5, 5, 5, 180);
            inviteIPS = new JButton[10];
            int ii = 0;
            for (Invitation invitation : userData.getUserInvitations()) {
                // for (int ii = 0; ii < 10; ii++) {
                gbcInvite.gridy = ii;
                gbcInvite.gridx = 0;
                inviteIPS[ii] = new JButton("Button " + invitation.getSenderID());
                pInv.add(inviteIPS[ii], gbcInvite);
                inviteIPS[ii].addActionListener(this);
                gbcInvite.gridx = 1;
                // ii++;
            }
            frame.add(backSwarmButton);
            frame.add(inviteList);
            frame.revalidate();
            frame.repaint();
        }
    }

    public ConnectScreen(GUI_Component frame) {
        // Chestii
        commandExecutor = Singleton.getSingletonObject().getCommandExecutor();
        userData = Singleton.getSingletonObject().getUserData();
        this.frame = frame;

        // SWARMS - PRINT BUTTON:
        printSwarmsButton = new JButton();
        printSwarmsButton.setText("VIEW SWARMS");
        printSwarmsButton.setBounds(540, 300, 200, 30);
        printSwarmsButton.setForeground(new Color(0x000000));
        printSwarmsButton.setBackground(new Color(0xB1B6A6));
        printSwarmsButton.setFocusable(false);
        printSwarmsButton.setFont(new Font("Radio Canada", Font.BOLD, 15));
        printSwarmsButton.addActionListener(this);

        // SWARMS - BACK BUTTON:
        backSwarmButton = new JButton();
        backSwarmButton.setText("BACK");
        backSwarmButton.setBounds(590, 635, 85, 30);
        backSwarmButton.setForeground(new Color(0x000000));
        backSwarmButton.setBackground(new Color(0xB1B6A6));
        backSwarmButton.setFocusable(false);
        backSwarmButton.setFont(new Font("Radio Canada", Font.BOLD, 15));
        backSwarmButton.addActionListener(this);

        // INVITES - PRINT BUTTON:
        viewInvitesButton = new JButton();
        viewInvitesButton.setText("VIEW INVITES");
        viewInvitesButton.setBounds(540, 350, 200, 30);
        viewInvitesButton.setForeground(new Color(0x000000));
        viewInvitesButton.setBackground(new Color(0xB1B6A6));
        viewInvitesButton.setFocusable(false);
        viewInvitesButton.setFont(new Font("Radio Canada", Font.BOLD, 15));
        viewInvitesButton.addActionListener(this);

        // INVITES - BACK BUTTON:
        backInvitesButton = new JButton();
        backInvitesButton.setText("BACK");
        backInvitesButton.setBounds(590, 635, 85, 30);
        backInvitesButton.setForeground(new Color(0x000000));
        backInvitesButton.setBackground(new Color(0xB1B6A6));
        backInvitesButton.setFocusable(false);
        backInvitesButton.setFont(new Font("Radio Canada", Font.BOLD, 15));
        backInvitesButton.addActionListener(this);

        // CREATE NEW SWARM BUTTON:
        newSwarmButton = new JButton();
        newSwarmButton.setText("CREATE NEW SWARM");
        newSwarmButton.setBounds(540, 450, 200, 30);
        newSwarmButton.setForeground(new Color(0x000000));
        newSwarmButton.setBackground(new Color(0xB1B6A6));
        newSwarmButton.setFocusable(false);
        newSwarmButton.setFont(new Font("Radio Canada", Font.BOLD, 15));
        newSwarmButton.addActionListener(this);

        // HELP BUTTON:
        helpButton = new JButton();
        helpButton.setText("HELP");
        helpButton.setBounds(540, 635, 85, 30);
        helpButton.setForeground(new Color(0x000000));
        helpButton.setBackground(new Color(0xB1B6A6));
        helpButton.setFocusable(false);
        helpButton.setFont(new Font("Radio Canada", Font.BOLD, 15));
        helpButton.addActionListener(this);

        // HELP CONTENTS:
        helpContents = new JLabel();
        helpContents.setText("TBD");
        helpContents.setForeground(new Color(0xB1B6A6));
        helpContents.setBounds(600, 150, 884, 500);
        helpContents.setFont(new Font("Radio Canada", Font.PLAIN, 32));

        // BACK HELP BUTTON:
        backHelpButton = new JButton();
        backHelpButton.setText("BACK");
        backHelpButton.setBounds(590, 635, 85, 30);
        backHelpButton.setForeground(new Color(0x000000));
        backHelpButton.setBackground(new Color(0xB1B6A6));
        backHelpButton.setFocusable(false);
        backHelpButton.setFont(new Font("Radio Canada", Font.BOLD, 15));
        backHelpButton.addActionListener(this);

        // QUIT BUTTON:
        quitButton = new JButton();
        quitButton.setText("QUIT");
        quitButton.setBounds(655, 635, 85, 30);
        quitButton.setForeground(new Color(0x000000));
        quitButton.setBackground(new Color(0xB1B6A6));
        quitButton.setFocusable(false);
        quitButton.setFont(new Font("Radio Canada", Font.BOLD, 15));
        quitButton.addActionListener(this);

        // TITLE:
        title = new JLabel();
        title.setText("P2P File Sync");
        title.setForeground(new Color(0xB1B6A6));
        title.setFont(new Font("Radio Canada", Font.BOLD, 96));
        title.setBounds(330, -100, 884, 500);

        // NULL INVITATIONS:
        nullContents = new JLabel();
        nullContents.setText("No invitations.");
        nullContents.setForeground(new Color(0xB1B6A6));
        nullContents.setFont(new Font("Radio Canada", Font.BOLD, 16));
        nullContents.setBounds(480, 560, 100, 30);
        nullContents.setVisible(true);

        // Invitation response buttons:
        acceptButton = new JButton();
        acceptButton.setText("Accept");
        acceptButton.setBounds(540, 585, 85, 30);
        acceptButton.setForeground(new Color(0x000000));
        acceptButton.setBackground(new Color(0xB1B6A6));
        acceptButton.setFocusable(false);
        acceptButton.setFont(new Font("Radio Canada", Font.BOLD, 15));
        acceptButton.addActionListener(this);

        declineButton = new JButton();
        declineButton.setText("Decline");
        declineButton.setBounds(655, 585, 85, 30);
        declineButton.setForeground(new Color(0x000000));
        declineButton.setBackground(new Color(0xB1B6A6));
        declineButton.setFocusable(false);
        declineButton.setFont(new Font("Radio Canada", Font.BOLD, 15));
        declineButton.addActionListener(this);

        joinButton = new JButton();
        joinButton.setText("Join");
        joinButton.setBounds(625, 585, 85, 30);
        joinButton.setForeground(new Color(0x000000));
        joinButton.setBackground(new Color(0xB1B6A6));
        joinButton.setFocusable(false);
        joinButton.setFont(new Font("Radio Canada", Font.BOLD, 15));
        joinButton.addActionListener(this);
        // --
        // Added elements:
        frame.add(title);
        frame.add(newSwarmButton);
        frame.add(viewInvitesButton);
        frame.add(printSwarmsButton);
        frame.add(helpButton);
        frame.add(quitButton);
        frame.revalidate();
        frame.repaint();
        // --
        }
    }
