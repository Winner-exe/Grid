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
    private Sprite gardenia, calem, red;
    private Timer timer;

    /**
     * Constructs the panel and begins animating the sprite sheet
     */
    public SpritePanel()
    {
        gardenia = new Sprite("gardenia-sprite.png", 4, 3);
        calem = new Sprite("calem-sprite.png", 4, 3);
        red = new Sprite("red-sprite.png", 4, 4);
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
        gardenia.draw((Graphics2D) g, 50, 50, this);
        calem.draw((Graphics2D) g, 100, 50, this);
        red.draw((Graphics2D) g, 50, 100, this);
    }
}
