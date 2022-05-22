package GUI;

import javax.swing.*;
import java.awt.*;

public class GUI_Component extends JFrame{
    ConnectScreen connectScreen;

    public GUI_Component() {
        // Frame init:
        this.setVisible(true);
        this.setSize(1280, 720);
        this.setTitle("P2P File Sync");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        ImageIcon image = new ImageIcon("sync_logo.jpg");
        this.setIconImage(image.getImage());
        this.getContentPane().setBackground(new Color(0x363946));
        this.setLayout(null);
        connectScreen = new ConnectScreen(this);
    }
}
