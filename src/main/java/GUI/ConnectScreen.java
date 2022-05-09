import javax.swing.*;
import java.awt.*;


public class ConnectScreen extends JPanel{
    GUI_Component frame;
    JLabel label;

    public ConnectScreen(GUI_Component frame){
        this.frame = frame;
        init();
    }

    private void init(){
        label = new JLabel("Test123");
        add(label);
    }
}
