import javax.swing.*;
import java.awt.event.ActionListener;

public class ArrowControls {
    public static JButton createArrowsButton(String direction, ActionListener action) {
        JButton button = new JButton(direction.equals("left") ? "<" : ">");
        button.addActionListener(action);
        button.setFocusable(false);
        button.setPreferredSize(new java.awt.Dimension(50, 50));  
        return button;
    }
}