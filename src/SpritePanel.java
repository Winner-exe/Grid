import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class SpritePanel extends JPanel implements ActionListener
{
    Sprite sprite;
    Timer timer;

    public SpritePanel()
    {
        sprite = new Sprite("sprite.png", 6, 9);
        timer = new Timer(500, this);

        this.repaint();
        timer.start();
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        this.repaint();
    }

    @Override
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        sprite.draw((Graphics2D) g, 50, 50, this);
    }
}
