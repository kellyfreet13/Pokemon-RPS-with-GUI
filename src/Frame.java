import javax.swing.*;
import java.awt.*;

/**
 * The frame class extends JFrame and creates a panel from which all user
 * interaction is performed.
 */
public class Frame extends JFrame {
    /**
     * Panel created for user interaction
     */
    private Panel p;

    /**
     * Constructs a Frame object and initializes the metrics of the window
     * Instantiates a new panel, and adds the panel to the content pane
     */
    public Frame(){
        setBounds(25, 25, 600, 450);
        p = new Panel();
        p.setBackground(Color.LIGHT_GRAY);
        getContentPane().add(p);
    }

    /**
     * Returns panel object so operations can be performed on it
     * @return this frame's panel
     */
    public Panel getPanel(){
        return p;
    }
}
