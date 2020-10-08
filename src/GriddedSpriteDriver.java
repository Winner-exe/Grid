import javax.swing.JFrame;

/**
 * Driver for the GriddedSprite project.
 *
 * @author Winston Lee
 */
public final class GriddedSpriteDriver
{
    public static void main(String[] args)
    {
        JFrame frame = new JFrame("Gridded Sprite");
        frame.setContentPane(new GriddedSpritePanel());
        frame.setVisible(true);
        frame.setFocusable(true);
        frame.setSize(1280, 1280);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
