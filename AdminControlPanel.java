import java.awt.*;
import javax.swing.*;


public class AdminControlPanel extends JFrame{
    public void initialize() {
        JLabel label = new JLabel("label");

        JPanel controlPanel = new JPanel();
        controlPanel.setLayout(new BorderLayout());
        controlPanel.setBackground(new Color(128, 128, 255));

        setTitle("Admin Control Panel");
        setSize(500, 600);
        setMinimumSize(new Dimension(300,400));
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
    }
}
