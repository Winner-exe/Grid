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
    private Sprite sprite;
    private Timer timer;

    /**
     * Constructs the panel and begins animating the sprite sheet
     */
    public SpritePanel()
    {
        sprite = new Sprite("sprite.png", 4, 3);
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
        sprite.draw((Graphics2D) g, 50, 50, this);
    }
}
