import javax.swing.*;
import java.awt.*;


public class CreateScreen extends JPanel{
    GUI_Component frame;
    JLabel label;

    public CreateScreen(GUI_Component frame){
        this.frame = frame;
        init();
    }

    private void init(){
        label = new JLabel("Test123");
        add(label);
    }
}