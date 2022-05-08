package GUI;

import javax.swing.*;
import java.awt.Color;

public class GUI_Component extends JFrame {
    // Declaration of other screens:
    ConnectScreen connectScreen;

    // Colors will be used as a 60-30-10% rule:
    // The background color will be Ash Gray (0xB1B6A6);
    // The elements color (such as additional elements) will be Dim Gray (0x696773)
    // The button colors will usually be Onyx (0x363946).
    // Frame initialization:
    public GUI_Component() {
        JFrame frame = new JFrame();
        setVisible(true);
        setTitle("P2P File Sync");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1280, 720);
        getContentPane().setBackground(new Color(0xB1B6A6));
        setLayout(null);

        connectScreen = new ConnectScreen(this);
        add(connectScreen);
    }
}
