import javax.swing.JFrame;
import java.io.IOException;

/**
 * Driver for the Grid project.
 *
 * @author Winston Lee
 */
public final class GridDriver {

	public static void main(String[] args) throws IOException {
		JFrame frame = new JFrame("Grid");
		frame.setContentPane(new Grid());
		frame.setVisible(true);
		frame.setFocusable(true);
		frame.setFocusTraversalKeysEnabled(false);
		frame.setSize(1280, 1280);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

}
