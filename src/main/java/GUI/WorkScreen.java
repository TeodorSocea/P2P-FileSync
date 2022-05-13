import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import static java.lang.System.exit;

public class WorkScreen extends JFrame implements ActionListener{
    private final GUI_Component frame;
    JLabel title;
    JButton testButton;

    // Button functions:
    public void actionPerformed(ActionEvent e) {
        // MEW SWARM BUTTON PRESS:
        if (e.getSource() == testButton) {
            System.out.println("// TEST");
        }
    }
    // --

    public WorkScreen(GUI_Component frame){
        this.frame = frame;
        // Test Button:
        testButton = new JButton();
        testButton.setText("TEST");
        testButton.setBounds(540, 350, 200, 30);
        testButton.setForeground(new Color(0x000000));
        testButton.setBackground(new Color(0xB1B6A6));
        testButton.setFocusable(false);
        testButton.setFont(new Font("Radio Canada", Font.BOLD, 15));
        testButton.addActionListener(this);
        // Title:
        title = new JLabel();
        title.setText("Work Screen");
        title.setForeground(new Color(0xB1B6A6));
        title.setFont(new Font("Radio Canada", Font.BOLD, 96));
        title.setBounds(330, -100, 884, 500);
        // --
        // Added elements:
        frame.add(title);
        frame.add(testButton);
        // --
        frame.revalidate();
        frame.repaint();
    }
}
