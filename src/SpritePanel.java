import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * Panel class for animating a sprite.
 *
 * @author Winston Lee
 */
public class SpritePanel extends JPanel implements ActionListener
{
    private Sprite red, ash, leaf;
    private Timer timer;

    /**
     * Constructs the panel and begins animating the sprite sheet.
     */
    public SpritePanel()
    {
        red = new Sprite("red-sprite.png", 4, 4);
        ash = new Sprite("ash-sprite.png", 4, 4);
        leaf = new Sprite("leaf-sprite.png", 4, 4);
        timer = new Timer(500, this);

        this.repaint();
        timer.start();
    }

    /**
     * Invoked when an action occurs.
     *
     * @param e the event to be processed
     */
    @Override
    public void actionPerformed(ActionEvent e)
    {
        this.repaint();
    }

    /**
     * Animates the sprite.
     *
     * @param g the <code>Graphics</code> object to protect
     */
    @Override
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        red.draw((Graphics2D) g, 50, 50, this);
        ash.draw((Graphics2D) g, 100, 50, this);
        leaf.draw((Graphics2D) g, 50, 100, this);
    }
}
