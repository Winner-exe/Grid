import javax.swing.JFrame;

public final class Driver {

	public static void main(String[] args)
	{
		JFrame frame = new JFrame("Grid");
		frame.setContentPane(new Grid());
		frame.setVisible(true);
		frame.setFocusable(true);
		frame.setSize(1280, 1280);
	}

}
