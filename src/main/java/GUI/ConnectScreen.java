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
    JButton printSwarmsButton;
    JPanel swarmList;
    JButton[] swarmElements;
    JButton viewInvitesButton;
    JPanel invitationList;
    JButton[] invitationElements;
    JButton newSwarmButton;
    JButton helpButton;
    JButton quitButton;
    JButton backButton;
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
            frame.add(backButton);
            frame.add(swarmList);
            frame.revalidate();
            frame.repaint();
        }
        // VIEW INVITES BUTTON PRESS:
        if (e.getSource() == viewInvitesButton){
            System.out.println("// VIEW INVITATIONS BUTTON PRESSED");
            frame.remove(newSwarmButton);
            frame.remove(viewInvitesButton);
            frame.remove(printSwarmsButton);
            frame.remove(helpButton);
            frame.remove(quitButton);
            frame.revalidate();
            frame.repaint();
            frame.add(backButton);
            frame.add(invitationList);
            frame.revalidate();
            frame.repaint();
        }
        // MEW SWARM BUTTON PRESS:
        if (e.getSource() == newSwarmButton){
            System.out.println("// CREATE NEW SWARM BUTTON PRESSED");
            frame.remove(newSwarmButton);
            frame.remove(viewInvitesButton);
            frame.remove(printSwarmsButton);
            frame.remove(helpButton);
            frame.remove(quitButton);
            frame.revalidate();
            frame.repaint();
            frame.add(backButton);
            frame.add(invitationList);
            frame.revalidate();
            frame.repaint();
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
            frame.add(backButton);
            frame.revalidate();
            frame.repaint();
        }
        // BACK BUTTON:
        if (e.getSource() == backButton){
            System.out.println("// BACK HELP BUTTON PRESSED");
            frame.remove(helpContents);
            frame.remove(backButton);
            frame.remove(invitationList);
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
        // QUIT PRESS:
        if (e.getSource() == quitButton){
            System.out.println("// QUIT BUTTON PRESSED, BYE!");
            exit(1);
        }
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
        // INVITE LIST:
        swarmList = new JPanel();
        swarmList.setLocation(530, 300);
        swarmList.setSize(200, 350);
        swarmList.setBackground(new Color(0x363946));
        swarmElements = new JButton[10];
        for (int i = 0; i <= 4; i ++){
            swarmElements[i] = new JButton();
            swarmElements[i].setBounds(10, 5 * (11*i + 19), 200, 30);
            swarmElements[i].setForeground(new Color(0x000000));
            swarmElements[i].setBackground(new Color(0xB1B6A6));
            swarmElements[i].setFocusable(false);
            swarmElements[i].setFont(new Font("Radio Canada", Font.BOLD, 15));
            swarmElements[i].setText("Swarm " + (i + 1));
            swarmList.add(swarmElements[i]);
        }
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
        invitationList = new JPanel();
        invitationList.setLocation(530, 300);
        invitationList.setSize(200, 350);
        invitationList.setBackground(new Color(0x363946));
        invitationElements = new JButton[10];
        for (int i = 0; i <= 5; i ++){
            invitationElements[i] = new JButton();
            invitationElements[i].setBounds(10, 5 * (11*i + 19), 200, 30);
            invitationElements[i].setForeground(new Color(0x000000));
            invitationElements[i].setBackground(new Color(0xB1B6A6));
            invitationElements[i].setFocusable(false);
            invitationElements[i].setFont(new Font("Radio Canada", Font.BOLD, 15));
            invitationElements[i].setText("Invite " + (i + 1));
            invitationList.add(invitationElements[i]);
        }
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
        backButton = new JButton();
        backButton.setText("BACK");
        backButton.setBounds(590, 635, 85, 30);
        backButton.setForeground(new Color(0x000000));
        backButton.setBackground(new Color(0xB1B6A6));
        backButton.setFocusable(false);
        backButton.setFont(new Font("Radio Canada", Font.BOLD, 15));
        backButton.addActionListener(this);
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
        // Title:
        title = new JLabel();
        title.setText("P2P File Sync");
        title.setForeground(new Color(0xB1B6A6));
        title.setFont(new Font("Radio Canada", Font.BOLD, 96));
        title.setBounds(330, -100, 884, 500);
        // --
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
