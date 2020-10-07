import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class GriddedSpritePanel extends JPanel implements ActionListener
{
    private GriddedSprite red, ash, leaf;
    private double scaleSlider;
    private Timer timer;

    public GriddedSpritePanel()
    {
        red = new GriddedSprite("red-sprite.png", 4, 4);

        ash = new GriddedSprite("ash-sprite.png", 4, 4);
        ash.setLocation(0, 500);
        scaleSlider = Math.PI / -2;

        leaf = new GriddedSprite("leaf-sprite.png", 4, 4);
        leaf.setLocation(500, 0);

        timer = new Timer(500, this);

        this.repaint();
        timer.start();
    }

    public void actionPerformed(ActionEvent e)
    {
        this.repaint();
    }

    @Override
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);

        red.translate(1, 1);
        ash.setScale(Math.sin(scaleSlider) + 2);
        leaf.rotate(Math.PI/180);

        red.draw((Graphics2D) g, this);
        ash.draw((Graphics2D) g, this);
        leaf.draw((Graphics2D) g, this);
    }
}
