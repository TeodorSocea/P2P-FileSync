package GUI;
import GUI.GUI_Component;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import static java.lang.System.exit;

public class ConnectScreen extends JFrame implements ActionListener {
    JButton newSwarmButton;
    JButton viewInvitesButton;
    JButton printSwarmsButton;
    JButton helpButton;
    JButton quitButton;
    JButton backButton;
    JLabel helpContents;
    JList inviteList;

    @Override
    public void actionPerformed(ActionEvent e){
        // MEW SWARM BUTTON PRESS:
        if (e.getSource() == newSwarmButton){
            System.out.println("// CREATE NEW SWARM BUTTON PRESSED");
        }
        // VIEW INVITES BUTTON PRESS:
        if (e.getSource() == viewInvitesButton){
            System.out.println("// VIEW INVITATIONS BUTTON PRESSED");
            this.remove(newSwarmButton);
            this.remove(viewInvitesButton);
            this.remove(printSwarmsButton);
            this.remove(helpButton);
            this.remove(quitButton);
            this.revalidate();
            this.repaint();
            this.add(backButton);
            this.add(inviteList);
        }
        // PRINT SWARMS PRESS:
        if (e.getSource() == printSwarmsButton){
            System.out.println("// PRINT SWARMS BUTTON PRESSED");
        }
        // HELP PRESS:
        if (e.getSource() == helpButton){
            System.out.println("// HELP BUTTON PRESSED");
            this.remove(newSwarmButton);
            this.remove(viewInvitesButton);
            this.remove(printSwarmsButton);
            this.remove(helpButton);
            this.remove(quitButton);
            this.revalidate();
            this.repaint();
            this.add(helpContents);
            this.add(backButton);
        }
        // BACK HELP BUTTON:
        if (e.getSource() == backButton){
            System.out.println("// BACK HELP BUTTON PRESSED");
            this.remove(helpContents);
            this.remove(backButton);
            revalidate();
            repaint();
            this.add(newSwarmButton);
            this.add(viewInvitesButton);
            this.add(printSwarmsButton);
            this.add(helpButton);
            this.add(quitButton);
        }
        // QUIT PRESS:
        if (e.getSource() == quitButton){
            System.out.println("// QUIT BUTTON PRESSED, BYE!");
            exit(1);
        }
    }

    Login_Screen() {
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
        JLabel title = new JLabel();
        title.setText("P2P File Sync");
        title.setForeground(new Color(0xB1B6A6));
        title.setFont(new Font("Radio Canada", Font.BOLD, 96));
        title.setBounds(330, -100, 884, 500);
        // --
        // Main Frame:
        this.setVisible(true);
        this.setSize(1280, 720);
        this.setTitle("P2P File Sync");
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.setResizable(false);
        ImageIcon image = new ImageIcon("sync_logo.jpg");
        this.setIconImage(image.getImage());
        this.getContentPane().setBackground(new Color(0x363946));
        this.setLayout(null);
        this.add(title);
        this.add(newSwarmButton);
        this.add(viewInvitesButton);
        this.add(printSwarmsButton);
        this.add(helpButton);
        this.add(quitButton);
        // --
    }
}
