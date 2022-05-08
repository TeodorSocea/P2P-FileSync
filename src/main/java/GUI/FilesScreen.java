package GUI;
import GUI.GUI_Component;

import javax.swing.*;
import java.awt.*;


public class FilesScreen extends JPanel{
    GUI_Component frame;
    JLabel label;

    public FilesScreen(GUI_Component frame){
        this.frame = frame;
        init();
    }

    private void init(){
        label = new JLabel("Test123");
        add(label);
    }
}