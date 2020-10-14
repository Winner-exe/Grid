import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * Panel class for animating a sprite with transformations.
 *
 * @author Winston Lee
 */
public class GriddedSpritePanel extends JPanel implements ActionListener
{
    private GriddedSprite red, ash, leaf;
    private double locationSlider; //Varies the location of the "red" sprite
    private double scaleSlider; //Varies the scale of the "ash" sprite
    private Timer timer;

    /**
     * Constructs the panel and begins animating the sprite sheet with transformations.
     */
    public GriddedSpritePanel()
    {
        red = new GriddedSprite("red-sprite.png", 4, 4);
        locationSlider = 0;

        ash = new GriddedSprite("ash-sprite.png", 4, 4);
        ash.setLocation(0, 500);
        scaleSlider = Math.PI / -2;

        leaf = new GriddedSprite("leaf-sprite.png", 4, 4);
        leaf.setLocation(500, 250);

        timer = new Timer(50, this);

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
     * Animates the sprite with transformations.
     *
     * @param g the <code>Graphics</code> object to protect
     */
    @Override
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);

        red.setLocation((int)(500*Math.sin(locationSlider)+500), (int)(250*Math.pow(Math.cos(locationSlider), 2)));
        locationSlider += Math.PI/36;
        ash.setScale(Math.sin(scaleSlider) + 2);
        scaleSlider += Math.PI / 18;
        leaf.rotate(Math.PI/36);

        red.draw((Graphics2D) g, this);
        ash.draw((Graphics2D) g, this);
        leaf.draw((Graphics2D) g, this);
    }
}
