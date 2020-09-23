import javax.swing.JFrame;

public class SpriteDriver
{
    public static void main(String[] args)
    {
        JFrame frame = new JFrame("Sprite");
        frame.setContentPane(new SpritePanel());
        frame.setVisible(true);
        frame.setFocusable(true);
        frame.setSize(1280, 1280);

    }
}
