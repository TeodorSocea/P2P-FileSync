package GUI;
import GUI.GUI_Component;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// Resident Daemon IMPORTS, keep commented for now:
/*import Resident_Daemon.CommandsPack.Command;
import Resident_Daemon.CommandsPack.CommandExecutor;
import Resident_Daemon.CommandsPack.Commands.*;
import Resident_Daemon.Core.Singleton;*/
// --

public class WorkScreen extends JFrame implements ActionListener{
    private final GUI_Component frame;
    ConnectScreen connectScreen;
    // Declared elements:
    JButton syncButton;
    JButton disconnectButton;
    JButton quitButton;
    JPanel membersText;
    JPanel ipList;
    JPanel swarm;
    JTextArea topText = new JTextArea("Members: ");
    // --
    // Button functions:
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == syncButton) {
            //SyncSwarm(Integer swarmID, Integer peerID) i dont know what is the meaning of the parameters
            //Command sync = new SyncSwarm();
            //commandExecutor.ExecuteOperation(sync);
        }
        if (e.getSource() == disconnectButton) {
            //Command exit = new ExitApp();
            //commandExecutor.ExecuteOperation(exit);

            //add all the buttons etc
            frame.remove(syncButton);
            frame.remove(disconnectButton);
            frame.remove(quitButton);
            frame.remove(membersText);
            frame.remove(ipList);
            frame.revalidate();
            frame.repaint();
            connectScreen = new ConnectScreen(frame);
        }
        if (e.getSource() == quitButton) {
            //Command exit = new ExitApp();
            //commandExecutor.ExecuteOperation(exit);
            //exit(1);
        }
    }
    // --
    public WorkScreen(GUI_Component frame){
        this.frame = frame;

        // Declaration for use of Commands, keep commented for now:
        // CommandExecutor commandExecutor = Singleton.getSingletonObject().getCommandExecutor();
        // If you need to use a command from the Resident Daemon team, you must do the following steps:
        // 1. Declare the command of your choice like:
        // Command nameofyourchoice = new NameOfCommand();
        // 2. Execute it like so:
        // commandExecutor.ExecuteOperation(nameofyourchoice).
        // Some commands require you to insert data, others send you the data, look at the command
        // you have to implement to understand what it does.
        // --

        // Elements:
        // Sync Button:
        syncButton = new JButton();
        syncButton.setText("Sync with Swarm");
        syncButton.setBounds(420, 350, 300, 30);
        syncButton.setForeground(new Color(0x000000));
        syncButton.setBackground(new Color(0xB1B6A6));
        syncButton.setFocusable(false);
        syncButton.setFont(new Font("Radio Canada", Font.BOLD, 15));
        syncButton.addActionListener(this);
        // --
        // Logout Button:
        disconnectButton = new JButton();
        disconnectButton.setText("Logout");
        disconnectButton.setBounds(420, 450, 125, 30);
        disconnectButton.setForeground(new Color(0x000000));
        disconnectButton.setBackground(new Color(0xB1B6A6));
        disconnectButton.setFocusable(false);
        disconnectButton.setFont(new Font("Radio Canada", Font.BOLD, 15));
        disconnectButton.addActionListener(this);
        // --
        // Quit Button:
        quitButton = new JButton();
        quitButton.setText("Quit");
        quitButton.setBounds(595, 450, 125, 30);
        quitButton.setForeground(new Color(0x000000));
        quitButton.setBackground(new Color(0xB1B6A6));
        quitButton.setFocusable(false);
        quitButton.setFont(new Font("Radio Canada", Font.BOLD, 15));
        quitButton.addActionListener(this);
        // --
        // Members Text:
        membersText = new JPanel();
        membersText.setBounds(1050,50,200,50);
        membersText.setForeground(Color.BLACK);
        membersText.setBackground(new Color(0xB1B6A6));
        membersText.setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, Color.black));
        topText.setFont(new Font("Radio Canada", Font.ITALIC, 25));
        topText.setForeground(new Color(0x000000));
        topText.setBackground(new Color(0xB1B6A6));
        topText.setEditable(false);
        membersText.add(topText);
        // --
        // IP List:
        ipList = new JPanel();
        JPanel p = new JPanel(new GridBagLayout());
        ipList.add(new JScrollPane(p,
                JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER));
        GridBagConstraints gbc = new GridBagConstraints();
        ipList.setLayout(new BoxLayout(ipList, BoxLayout.PAGE_AXIS));
        ipList.setBounds(1050,98,200,525);
        ipList.setMaximumSize(new Dimension(100, 200));
        p.setFont(new Font("Radio Canada", Font.ITALIC, 25));
        p.setForeground(new Color(0x000000));
        p.setBackground(new Color(0xB1B6A6));
        ipList.setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, Color.black));
        ipList.setAutoscrolls(true);
        gbc.insets = new Insets(5,5,5,180);
        for (int ii=0; ii<100; ii++) {
            gbc.gridy = ii;
            gbc.gridx = 0;
            p.add(new JLabel("- ip  " + (ii+1)), gbc);
            gbc.gridx = 1;
        }
        // --
        // Added elements:
        frame.add(syncButton);
        frame.add(disconnectButton);
        frame.add(quitButton);
        frame.add(membersText);
        frame.add(ipList);
        // --
        frame.revalidate();
        frame.repaint();
    }
}
