package GUI;
import GUI.GUI_Component;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import static java.lang.System.exit;

// Resident Daemon IMPORTS, keep commented for now:
/*import Resident_Daemon.CommandsPack.Command;
import Resident_Daemon.CommandsPack.CommandExecutor;
import Resident_Daemon.CommandsPack.Commands.*;
import Resident_Daemon.Core.Singleton;*/
// --

public class ConnectScreen extends JFrame implements ActionListener {
    private final GUI_Component frame;
    WorkScreen workScreen;
    JLabel title;
    // SWARM BUTTONS:
    JButton printSwarmsButton;
    JPanel swarmList;
    JButton[] swarmIPS;
    JButton backSwarmButton;
    // --
    // INVITES ELEMENTS:
    JButton viewInvitesButton;
    JPanel inviteList;
    JButton[] inviteIPS;
    JButton backInvitesButton;
    // --
    JButton newSwarmButton;
    JButton helpButton;
    JButton backHelpButton;
    JButton quitButton;
    JButton tempTransitionButton;
    JLabel helpContents;

    @Override
    public void actionPerformed(ActionEvent e){
        // PRINT SWARMS PRESS:
        if (e.getSource() == printSwarmsButton){
            System.out.println("// PRINT SWARMS BUTTON PRESSED");
            frame.remove(newSwarmButton);
            frame.remove(viewInvitesButton);
            frame.remove(printSwarmsButton);
            frame.remove(helpButton);
            frame.remove(quitButton);
            frame.revalidate();
            frame.repaint();
            frame.add(backHelpButton);
            frame.add(swarmList);
            frame.revalidate();
            frame.repaint();
        }
        // --
        // INVITE SWARMS BUTTON PRESSED:
        for (int i = 0; i < swarmIPS.length; i++) {
            if (e.getSource() == swarmIPS[i]){
                System.out.println("// SWARM IP PRESSED: " + swarmIPS[i].getText());
            }
        }
        // BACK : SWARM IPS SCREEN:
        if (e.getSource() == backSwarmButton){
            System.out.println("// BACK SWARM IPS BUTTON PRESSED");
            frame.remove(backSwarmButton);
            frame.remove(swarmList);
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
        // VIEW INVITES BUTTON PRESS:
        if (e.getSource() == viewInvitesButton) {
            System.out.println("// VIEW INVITATIONS BUTTON PRESSED");
            frame.remove(newSwarmButton);
            frame.remove(viewInvitesButton);
            frame.remove(printSwarmsButton);
            frame.remove(helpButton);
            frame.remove(quitButton);
            frame.revalidate();
            frame.repaint();
            frame.add(backSwarmButton);
            frame.add(inviteList);
            frame.revalidate();
            frame.repaint();
        }
        // --
        // INVITE IP BUTTON PRESSED:
        for (int j = 0; j < inviteIPS.length; j++) {
            if (e.getSource() == inviteIPS[j]){
                System.out.println("I AM HERE" + j);
            }
        }
        // BACK : INVITES IPS SCREEN:
        if (e.getSource() == backInvitesButton) {
            System.out.println("// BACK SWARM IPS BUTTON PRESSED");
            frame.remove(backInvitesButton);
            frame.remove(inviteList);
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
        // --
        // MEW SWARM BUTTON PRESS:
        if (e.getSource() == newSwarmButton){
            System.out.println("// CREATE NEW SWARM BUTTON PRESSED");
        }
        // --
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
        // --
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
        // --
        // QUIT PRESS:
        if (e.getSource() == quitButton){
            System.out.println("// QUIT BUTTON PRESSED, BYE!");
            exit(1);
        }
        // --
        // TRANSITION BUTTON TEMP:
        if (e.getSource() == tempTransitionButton){
            System.out.println("// switching...");
            frame.remove(newSwarmButton);
            frame.remove(viewInvitesButton);
            frame.remove(printSwarmsButton);
            frame.remove(helpButton);
            frame.remove(tempTransitionButton);
            frame.remove(title);
            frame.remove(quitButton);
            frame.revalidate();
            frame.repaint();
            workScreen = new WorkScreen(frame);
        }
        // --
    }

    public ConnectScreen(GUI_Component frame) {
        this.frame = frame;
        // TEMP TRANSITION BUTTON:
        tempTransitionButton = new JButton();
        tempTransitionButton.setText("TEMP: TRANSITION TO WORK SCREEN");
        tempTransitionButton.setBounds(100, 100, 200, 30);
        tempTransitionButton.setForeground(new Color(0x000000));
        tempTransitionButton.setBackground(new Color(0xB1B6A6));
        tempTransitionButton.setFocusable(false);
        tempTransitionButton.setFont(new Font("Radio Canada", Font.BOLD, 15));
        tempTransitionButton.addActionListener(this);
        // --
        // PRINT SWARMS BUTTON:
        printSwarmsButton = new JButton();
        printSwarmsButton.setText("VIEW SWARMS");
        printSwarmsButton.setBounds(540, 300, 200, 30);
        printSwarmsButton.setForeground(new Color(0x000000));
        printSwarmsButton.setBackground(new Color(0xB1B6A6));
        printSwarmsButton.setFocusable(false);
        printSwarmsButton.setFont(new Font("Radio Canada", Font.BOLD, 15));
        printSwarmsButton.addActionListener(this);
        // --
        // SWARM LIST:
        swarmList = new JPanel();
        JPanel pSwm = new JPanel(new GridBagLayout());
        swarmList.add(new JScrollPane(pSwm, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER));
        GridBagConstraints gbcSwarm = new GridBagConstraints();
        swarmList.setLayout(new BoxLayout(swarmList, BoxLayout.PAGE_AXIS));
        swarmList.setBounds(566,250,150,250);
        swarmList.setMaximumSize(new Dimension(100, 200));
        swarmList.setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, new Color(0x363946)));
        pSwm.setFont(new Font("Radio Canada", Font.ITALIC, 25));
        pSwm.setForeground(new Color(0x000000));
        pSwm.setBackground(new Color(0x363946));
        swarmList.setAutoscrolls(true);
        gbcSwarm.insets = new Insets(5,5,5,180);
        swarmIPS = new JButton[10];
        for (int ii=0; ii<10; ii++) {
            gbcSwarm.gridy = ii;
            gbcSwarm.gridx = 0;
            swarmIPS[ii] = new JButton("Button " + ii);
            pSwm.add(swarmIPS[ii], gbcSwarm);
            swarmIPS[ii].addActionListener(this);
            gbcSwarm.gridx = 1;
        }
        // --
        // BACK SWARM BUTTON:
        backSwarmButton = new JButton();
        backSwarmButton.setText("BACK");
        backSwarmButton.setBounds(590, 635, 85, 30);
        backSwarmButton.setForeground(new Color(0x000000));
        backSwarmButton.setBackground(new Color(0xB1B6A6));
        backSwarmButton.setFocusable(false);
        backSwarmButton.setFont(new Font("Radio Canada", Font.BOLD, 15));
        backSwarmButton.addActionListener(this);
        // --
        // VIEW INVITATIONS BUTTON:
        viewInvitesButton = new JButton();
        viewInvitesButton.setText("VIEW INVITES");
        viewInvitesButton.setBounds(540, 350, 200, 30);
        viewInvitesButton.setForeground(new Color(0x000000));
        viewInvitesButton.setBackground(new Color(0xB1B6A6));
        viewInvitesButton.setFocusable(false);
        viewInvitesButton.setFont(new Font("Radio Canada", Font.BOLD, 15));
        viewInvitesButton.addActionListener(this);
        // --
        // INVITE LIST:
        inviteList = new JPanel();
        JPanel pInv = new JPanel(new GridBagLayout());
        inviteList.add(new JScrollPane(pInv, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER));
        GridBagConstraints gbcInvite = new GridBagConstraints();
        inviteList.setLayout(new BoxLayout(inviteList, BoxLayout.PAGE_AXIS));
        inviteList.setBounds(566,250,150,250);
        inviteList.setMaximumSize(new Dimension(100, 200));
        inviteList.setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, new Color(0x363946)));
        pInv.setFont(new Font("Radio Canada", Font.ITALIC, 25));
        pInv.setForeground(new Color(0x000000));
        pInv.setBackground(new Color(0x363946));
        inviteList.setAutoscrolls(true);
        gbcInvite.insets = new Insets(5,5,5,180);
        inviteIPS = new JButton[10];
        for (int ii=0; ii<10; ii++) {
            gbcInvite.gridy = ii;
            gbcInvite.gridx = 0;
            inviteIPS[ii] = new JButton("Button " + ii);
            pInv.add(inviteIPS[ii], gbcInvite);
            inviteIPS[ii].addActionListener(this);
            gbcInvite.gridx = 1;
        }
        // --
        // BACK INVITES BUTTON:
        backInvitesButton = new JButton();
        backInvitesButton.setText("BACK");
        backInvitesButton.setBounds(590, 635, 85, 30);
        backInvitesButton.setForeground(new Color(0x000000));
        backInvitesButton.setBackground(new Color(0xB1B6A6));
        backInvitesButton.setFocusable(false);
        backInvitesButton.setFont(new Font("Radio Canada", Font.BOLD, 15));
        backInvitesButton.addActionListener(this);
        // --
        // CREATE NEW SWARM BUTTON:
        newSwarmButton = new JButton();
        newSwarmButton.setText("CREATE NEW SWARM");
        newSwarmButton.setBounds(540, 450, 200, 30);
        newSwarmButton.setForeground(new Color(0x000000));
        newSwarmButton.setBackground(new Color(0xB1B6A6));
        newSwarmButton.setFocusable(false);
        newSwarmButton.setFont(new Font("Radio Canada", Font.BOLD, 15));
        newSwarmButton.addActionListener(this);
        // --
        // HELP BUTTON:
        helpButton = new JButton();
        helpButton.setText("HELP");
        helpButton.setBounds(540, 635, 85, 30);
        helpButton.setForeground(new Color(0x000000));
        helpButton.setBackground(new Color(0xB1B6A6));
        helpButton.setFocusable(false);
        helpButton.setFont(new Font("Radio Canada", Font.BOLD, 15));
        helpButton.addActionListener(this);
        // --
        // HELP CONTENTS:
        helpContents = new JLabel();
        helpContents.setText("TBD");
        helpContents.setForeground(new Color(0xB1B6A6));
        helpContents.setBounds(600, 150, 884, 500);
        helpContents.setFont(new Font("Radio Canada", Font.PLAIN, 32));
        // --
        // BACK HELP BUTTON:
        backHelpButton = new JButton();
        backHelpButton.setText("BACK");
        backHelpButton.setBounds(590, 635, 85, 30);
        backHelpButton.setForeground(new Color(0x000000));
        backHelpButton.setBackground(new Color(0xB1B6A6));
        backHelpButton.setFocusable(false);
        backHelpButton.setFont(new Font("Radio Canada", Font.BOLD, 15));
        backHelpButton.addActionListener(this);
        // --
        // QUIT BUTTON:
        quitButton = new JButton();
        quitButton.setText("QUIT");
        quitButton.setBounds(655, 635, 85, 30);
        quitButton.setForeground(new Color(0x000000));
        quitButton.setBackground(new Color(0xB1B6A6));
        quitButton.setFocusable(false);
        quitButton.setFont(new Font("Radio Canada", Font.BOLD, 15));
        quitButton.addActionListener(this);
        // --
        // TITLE:
        title = new JLabel();
        title.setText("P2P File Sync");
        title.setForeground(new Color(0xB1B6A6));
        title.setFont(new Font("Radio Canada", Font.BOLD, 96));
        title.setBounds(330, -100, 884, 500);
        // --
        // Added elements:
        frame.add(title);
        frame.add(newSwarmButton);
        frame.add(viewInvitesButton);
        frame.add(printSwarmsButton);
        frame.add(helpButton);
        frame.add(quitButton);
        frame.add(tempTransitionButton);
        frame.revalidate();
        frame.repaint();
        // --
    }
}
