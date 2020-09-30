import java.util.Scanner;
import javax.swing.JFrame;

/**
 * Driver for the Grid project.
 * 
 * @author Winston Lee
 */
public final class GridDriver {

	public static void main(String[] args)
	{
		Scanner scan = new Scanner(System.in);
		System.out.print("Rows: ");
		int rows = scan.nextInt();
		System.out.print("Columns: ");
		int columns = scan.nextInt();

		JFrame frame = new JFrame("Grid");
		frame.setContentPane(new Grid(rows, columns));
		frame.setVisible(true);
		frame.setFocusable(true);
		frame.setSize(1280, 1280);
	}

}
