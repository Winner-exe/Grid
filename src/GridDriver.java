import javax.swing.JFrame;

/*
 * Driver for the Grid project.
 * 
 * @author Winston Lee
 */
public final class GridDriver {

	public static void main(String[] args)
	{
		JFrame frame = new JFrame("Grid");
		frame.setContentPane(new Grid());
		frame.setVisible(true);
		frame.setFocusable(true);
		frame.setSize(1280, 1280);
	}

}
