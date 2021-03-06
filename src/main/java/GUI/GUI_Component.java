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
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.addWindowListener(new CustomWindowListener());
        this.setResizable(false);

        this.getContentPane().setBackground(new Color(0x131c30));
        ImageIcon image = new ImageIcon("src/main/java/GUI/sync_logo.jpg");
        /*this.setIconImage(image.getImage());
        Image img = Toolkit.getDefaultToolkit().getImage(("src/main/java/GUI/backgroundImg.png"));
        this.setContentPane(new JPanel() {
            @Override
            public void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(img, 0, 0, null);
            }
        });*/
        this.setLayout(null);
        connectScreen = new ConnectScreen(this);
    }
}
